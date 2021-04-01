package com.example.coolieweather.presentation.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
@HiltAndroidApp
class CoolieWeatherApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(MyDebugTree())

    }


    class MyDebugTree : Timber.DebugTree() {

        override fun createStackElementTag(element: StackTraceElement): String? {
            return String.format(
                " [M:%s] [C:%s]",
                element.methodName,
                super.createStackElementTag(element)
            )
        }
    }

}