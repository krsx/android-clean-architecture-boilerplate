package com.project.android_clean_architecture_boilerplate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}