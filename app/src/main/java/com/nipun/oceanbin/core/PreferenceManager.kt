package com.nipun.oceanbin.core

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto.WeatherDto
import java.util.*

/*
 * Class for managing shared preferences
 */
class PreferenceManager(private val context: Context) {
    companion object {
        const val IS_INSTALLED = "is_installed"
        const val HOURLY_KEY = "hourly_key"
        const val CURRENT_LOCATION_KEY = "current_location_key"
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

    fun saveWeather(key: String = Constant.WEATHER_KEY, value: WeatherDto) {
        with(sharedPreference.edit()) {
            val gson = Gson()
            putString(key, gson.toJson(value))
            commit()
        }
    }

    fun getWeather(key: String = Constant.WEATHER_KEY): WeatherDto? {
        val gson = Gson()
        val str = sharedPreference.getString(key, "")
        try {
            if (str.isNullOrEmpty()) return null
            return gson.fromJson(str, WeatherDto::class.java)
        } catch (e: Exception) {
            return null
        }
    }

    fun saveCurrentLocation(key: String = CURRENT_LOCATION_KEY,value: LatLng){
        with(sharedPreference.edit()) {
            val gson = Gson()
            putString(key, gson.toJson(value))
            commit()
        }
    }

    fun getCurrentLocation(key: String = CURRENT_LOCATION_KEY) : LatLng{
        val gson = Gson()
        val str = sharedPreference.getString(key, "")
        try {
            if (str.isNullOrEmpty()) return LatLng(0.0,0.0)
            return gson.fromJson(str, LatLng::class.java)
        } catch (e: Exception) {
            return LatLng(0.0,0.0)
        }
    }

    fun getAddress(longitude: Double, latitude: Double): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addressList = geoCoder.getFromLocation(latitude, longitude, 1)
        return if (addressList.isNullOrEmpty()) ""
        else {
            val address: Address = addressList[0]
            return address.toSearchModel().getName()
        }
    }
}

