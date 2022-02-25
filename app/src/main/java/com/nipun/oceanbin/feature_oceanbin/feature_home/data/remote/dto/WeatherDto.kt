package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto

import java.io.Serializable

data class WeatherDto(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val timeStamp : Long = 0L
) : Serializable{
    fun isCallApi() : Boolean{
        val currentTime = System.currentTimeMillis()
        return (currentTime>(timeStamp+3600000))
    }
}