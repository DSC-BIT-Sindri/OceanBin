package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.HourlyModel

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val snow: Snow,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
){
    fun toHourlyModel() : HourlyModel{
        return HourlyModel(
            iconId = "https://raw.githubusercontent.com/nipun2003/images/9340eb30c7096b1a010f4ea7b0a4b7afe7ce013d/${weather[0].icon}.svg",
            temp = feels_like.toInt(),
            humidity = humidity
        )
    }
}