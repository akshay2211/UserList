package io.ak1.userlist.data.local

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */

//Worker class initiates after 45 minutes to clear database
class ClearDatabase(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams), KoinComponent {
    private val database: AppDatabase by inject()
    private val coroutineContext: CoroutineContext by inject()
    override fun doWork(): Result {
        CoroutineScope(coroutineContext).launch {
            database.userDao().deleteTable()
        }
        return Result.success()
    }
}
