package com.example.soptseminar1

import android.content.Context
import android.content.SharedPreferences

object SOPTSharedPreferences {
    private const val STORAGE_KEY = "USER_AUTH"
    private const val AUTO_LOGIN = "AUTO_LOGIN"

    fun getSharedPreference(context: Context): SharedPreferences{
        return context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)
    }

    fun getAutoLogin(context: Context): Boolean {
        return getSharedPreference(context).getBoolean(AUTO_LOGIN, false)
    }

    fun setAutoLogin(context: Context, value: Boolean) {
        getSharedPreference(context)
            .edit()
            .putBoolean(AUTO_LOGIN, value)
            .apply()
    }

    fun setLogout(context: Context) {
        getSharedPreference(context)
            .edit()
            .remove(AUTO_LOGIN)
            .clear()
            .apply()
    }
}