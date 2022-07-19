package me.brunofelix.cleandictionary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.brunofelix.cleandictionary.extension.changeTheme
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        changeTheme()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
