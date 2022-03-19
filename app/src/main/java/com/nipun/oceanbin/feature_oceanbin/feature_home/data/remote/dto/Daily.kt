package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.DayModel

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val snow: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
){
    fun toDailyModel() : DayModel{
        return DayModel(
            timeStamp = dt,
            iconId = "https://raw.githubusercontent.com/nipun2003/images/9340eb30c7096b1a010f4ea7b0a4b7afe7ce013d/${weather[0].icon}.svg",
            minTemp = temp.min.toInt(),
            maxTemp = temp.max.toInt(),
            humidity = humidity
        )
    }
}