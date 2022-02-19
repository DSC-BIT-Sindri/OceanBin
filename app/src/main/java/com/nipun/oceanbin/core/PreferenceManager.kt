package com.nipun.oceanbin.core

import android.content.Context


class PreferenceManager(private val context : Context) {
    companion object{
        const val IS_INSTALLED = "is_installed"
    }
    private val sharedPreference = context.getSharedPreferences("Settings",Context.MODE_PRIVATE)

    fun saveBoolean(key : String = IS_INSTALLED,value : Boolean){
        with(sharedPreference.edit()){
            putBoolean(key,value)
            apply()
        }
    }

    fun getBoolean(key: String = IS_INSTALLED) : Boolean{
        return sharedPreference.getBoolean(key,true)
    }
}

