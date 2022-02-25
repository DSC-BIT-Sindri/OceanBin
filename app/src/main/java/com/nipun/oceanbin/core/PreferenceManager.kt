package com.nipun.oceanbin.core

import android.content.Context
import com.google.gson.Gson
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.HourlyDataModel
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.WeatherModel

/*
 * Class for managing shared preferences
 */
class PreferenceManager(private val context: Context) {
    companion object {
        const val IS_INSTALLED = "is_installed"
        const val HOURLY_KEY = "hourly_key"
    }

    private val sharedPreference = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    fun saveBoolean(key: String = IS_INSTALLED, value: Boolean) {
        with(sharedPreference.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String = IS_INSTALLED, default: Boolean = true): Boolean {
        return sharedPreference.getBoolean(key, default)
    }

    fun saveInteger(key: String, value: Int) {
        with(sharedPreference.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun getInt(key: String, default: Int = 0): Int {
        return sharedPreference.getInt(
            key, default
        )
    }

    fun saveWeather(key: String = Constant.WEATHER_KEY, value: WeatherModel) {
        with(sharedPreference.edit()) {
            val gson = Gson()
            putString(key, gson.toJson(value))
            commit()
        }
    }

    fun getWeather(key: String = Constant.WEATHER_KEY): WeatherModel {
        val gson = Gson()
        val str = sharedPreference.getString(key, "")
        try {
            if (str.isNullOrEmpty()) return WeatherModel()
            return gson.fromJson(str, WeatherModel::class.java)
        } catch (e: Exception) {
            return WeatherModel()
        }
    }

    fun saveHourlyUpdate(key: String = HOURLY_KEY,value: HourlyDataModel){
        with(sharedPreference.edit()){
            val gson = Gson()
            putString(key, gson.toJson(value))
            commit()
        }
    }

    fun getHourlyUpdate(key: String = HOURLY_KEY) : HourlyDataModel {
        val gson = Gson()
        val str = sharedPreference.getString(key, "")
        try {
            if (str.isNullOrEmpty()) return HourlyDataModel(
                emptyList()
            )
            return gson.fromJson(str, HourlyDataModel::class.java)
        } catch (e: Exception) {
            return HourlyDataModel(emptyList())
        }
    }
}

