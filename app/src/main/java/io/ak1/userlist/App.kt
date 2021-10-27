package io.ak1.userlist

import androidx.multidex.MultiDexApplication
import io.ak1.userlist.di.databaseModule
import io.ak1.userlist.di.networkModule
import io.ak1.userlist.di.repoModule
import io.ak1.userlist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by akshay on 27/10/21
 * https://ak1.io
 */

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(applicationContext)
            // use Android logger - Level.INFO by default
            // androidLogger(Level.DEBUG)
            koin.loadModules(listOf(databaseModule, networkModule, viewModelModule, repoModule))
        }
    }
}