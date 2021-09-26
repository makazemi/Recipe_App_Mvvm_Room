package com.makazemi.cooking.base


import androidx.multidex.MultiDexApplication
import com.makazemi.cooking.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class BaseApplication :MultiDexApplication(){


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}