package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

import com.nipun.oceanbin.feature_oceanbin.feature_weather.models.SmallWeatherCardDetails
import java.io.Serializable

data class HourlyModel(
    val iconId : String,
    val temp : Int,
    val humidity : Int,
    var time : String = ""
) : Serializable{
    fun toSmallWeatherCardDetails() : SmallWeatherCardDetails {
        return SmallWeatherCardDetails(
            imageId = iconId,
            percentage = humidity,
            time = time
        )
    }
}