package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.WeatherModel

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val sunrise : Long = 0L,
    val sunset : Long = 0L,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val snow: Snow,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
) {
    fun toWeatherModel() : WeatherModel{
        return WeatherModel(
            temperature = feels_like.toInt(),
            weather = weather[0].description,
            iconId = "https://raw.githubusercontent.com/nipun2003/images/9340eb30c7096b1a010f4ea7b0a4b7afe7ce013d/${weather[0].icon}.svg"
        )
    }
}