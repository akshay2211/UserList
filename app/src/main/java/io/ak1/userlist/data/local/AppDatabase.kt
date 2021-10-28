package io.ak1.userlist.data.local

import androidx.paging.PagingSource
import androidx.room.*
import io.ak1.userlist.data.remote.ApiList
import io.ak1.userlist.models.RemoteKey
import io.ak1.userlist.models.User

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */

@Database(entities = [User::class, RemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUserList(): PagingSource<Int, User>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUserListSimple(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(images: List<User>)

    @Query("SELECT MAX(pageNum) FROM user_table")
    fun getNextPage(): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteTable()

}

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE label = :query")
    suspend fun remoteKeyByQuery(query: String = ApiList.END_POINT): RemoteKey

    @Query("DELETE FROM remote_keys WHERE label = :query")
    suspend fun deleteByQuery(query: String = ApiList.END_POINT)
}
