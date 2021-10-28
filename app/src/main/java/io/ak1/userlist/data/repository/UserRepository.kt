package io.ak1.userlist.data.repository

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import io.ak1.userlist.data.local.AppDatabase
import io.ak1.userlist.data.remote.ApiList
import io.ak1.userlist.models.BaseData
import io.ak1.userlist.models.RemoteKey
import io.ak1.userlist.models.User
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */

//val currentTime = System.currentTimeMillis();

@OptIn(ExperimentalPagingApi::class)
class ExampleRemoteMediator(
    private val repository: UserRepository
) : RemoteMediator<Int, User>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        //Log.e("currentTime","-> $currentTime")
        return if (System.currentTimeMillis() - 1635416853142 <= cacheTimeout) {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            Log.e("SKIP_INITIAL_REFRESH", "InitializeAction -> SKIP_INITIAL_REFRESH")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            Log.e("LAUNCH_INITIAL_REFRESH", "InitializeAction -> LAUNCH_INITIAL_REFRESH")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    // val remoteKey = repository.getKey()

                    Log.e("load", "LoadType.REFRESH")
                    null
                }
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> {
                    Log.e("load", "LoadType.PREPEND")
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND -> {

                    val remoteKey = repository.getRemoteKey()
                    Log.e("load", "LoadType.APPEND $remoteKey")

                    // You must explicitly check if the page key is null when
                    // appending, since null is only valid for initial load.
                    // If you receive null for APPEND, that means you have
                    // reached the end of pagination and there are no more
                    // items to load.
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextKey
                }
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val response = repository.getUsers(loadKey ?: "")

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            repository.insertData(response.body(), loadType = loadType)

            MediatorResult.Success(
                endOfPaginationReached = response.body()?.page == response.body()?.total
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}


class UserRepository(private val appDatabase: AppDatabase, private val apiList: ApiList) {
    private val userDao = appDatabase.userDao()
    private val remoteKeyDao = appDatabase.remoteKeyDao()
    suspend fun getKey(): Int {
        return appDatabase.withTransaction {
            userDao.getNextPage()
        }
    }

    suspend fun getUsers(page: String) = apiList.getUserList(page = page)
    suspend fun insertData(response: BaseData?, loadType: LoadType) {
        response?.let {
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.deleteTable()
                    remoteKeyDao.deleteByQuery()
                }
                // Update RemoteKey for this query.
                remoteKeyDao.insertOrReplace(
                    RemoteKey(ApiList.END_POINT, "${response.page + 1}")
                )


                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                userDao.insert(response.data)
            }
        }

    }

    suspend fun getRemoteKey(): RemoteKey? {
        return appDatabase.withTransaction {
            remoteKeyDao.remoteKeyByQuery()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = 50),
        remoteMediator = ExampleRemoteMediator(this)
    ) {
        userDao.getUserList()
    }

}