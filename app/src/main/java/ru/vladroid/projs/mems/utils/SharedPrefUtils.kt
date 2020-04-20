package ru.vladroid.projs.mems.utils

import android.content.Context

class SharedPrefUtils(context: Context) {
    private val storageName = "mems_user_info"
    private val sp = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String) = sp.getString(key, defValue)

    fun putString(key: String, value: String) {
        sp.edit()
            .putString(key, value)
            .apply()
    }

    fun getInt(key: String, defValue: Int) = sp.getInt(key, defValue)

    fun putInt(key: String, value: Int) {
        sp.edit()
            .putInt(key, value)
            .apply()
    }
}