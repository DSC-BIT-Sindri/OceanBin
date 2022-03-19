package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

import com.nipun.oceanbin.feature_oceanbin.feature_weather.models.SmallWeatherCardDetails
import java.text.SimpleDateFormat
import java.util.*

data class DayModel(
    val timeStamp : Long,
    val iconId : String,
    val minTemp : Int,
    val maxTemp : Int,
    val humidity : Int
){
    fun getDay(index : Int) : String{
        if (index == 0) return "Today"
        val sdf = SimpleDateFormat("MM/dd yyyy HH:mm:ss", Locale.ENGLISH)
        return sdf.format(timeStamp*1000L).substring(0,5)
    }

    fun toSmallWeatherCardDetail(index: Int) : SmallWeatherCardDetails {
        return SmallWeatherCardDetails(
            imageId = iconId,
            percentage = humidity,
            time = if(index == 0) "Today" else getDay(index)
        )
    }
}
