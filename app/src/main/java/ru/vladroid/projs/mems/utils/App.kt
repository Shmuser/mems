package ru.vladroid.projs.mems.utils

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPrefUtils = SharedPrefUtils(this)
    }

    companion object {
        private lateinit var sharedPrefUtils: SharedPrefUtils

        fun getInstance(): SharedPrefUtils {
            return sharedPrefUtils
        }
    }
}