package com.esmaeel.catchathief

import android.app.Application
import com.esmaeel.catchathief.Utils.MyUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val logger = object : AndroidLogAdapter(
            MyUtils.getFormatter()
        ) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        }

        Logger.addLogAdapter(logger)
    }
}