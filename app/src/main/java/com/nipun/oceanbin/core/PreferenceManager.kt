package com.nipun.oceanbin.core

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.nipun.oceanbin.core.firebase.User
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
        private val USER_ID = "user_id"
        private const val USER_DETAIL = "user_details"
        const val READ_PREVIOUS_DENIED = "read_previous_denied"
    }

    private val sharedPreference = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    fun saveString(key : String = USER_ID,value: String ){
        with(sharedPreference.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String = USER_ID) : String{
        return sharedPreference.getString(key,"")?:""
    }

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

    fun saveUser(key: String = USER_DETAIL,value: User){
        with(sharedPreference.edit()) {
            val gson = Gson()
            putString(key, gson.toJson(value))
            commit()
        }
    }

    fun getUser() : User?{
        val gson = Gson()
        val str = sharedPreference.getString(USER_DETAIL, "")
        try {
            if (str.isNullOrEmpty()) return null
            return gson.fromJson(str, User::class.java)
        } catch (e: Exception) {
            return null
        }
    }

    fun deleteUser(){
        with(sharedPreference.edit()){
            remove(USER_DETAIL)
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

    fun getAddress(longitude: Double, latitude: Double,isLine : Boolean = false): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addressList = geoCoder.getFromLocation(latitude, longitude, 1)
        return if (addressList.isNullOrEmpty()) ""
        else {
            val address: Address = addressList[0]
            return if(isLine) address.toSearchModel().addressLine?:"" else address.toSearchModel().getName()
        }
    }
}

