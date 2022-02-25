package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class WeatherModel(
    var location: String = "Bariatu, Ranchi",
    var temperature: Int = 25,
    var weather: String = "Shiny Day",
    var timeStamp : Long = 0L,
    var iconId : String = ""
) {

    fun getCurrentDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatTime = currentTime.format(formatter).substring(0, 12)
            LocalDateTime.now().dayOfWeek.name + ", " + if (formatTime.get(formatTime.lastIndex) == ',') formatTime.substring(
                0,
                formatTime.lastIndex
            ) else formatTime
        } else {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        }
    }

    fun isCallApi() : Boolean{
        val currentTime = System.currentTimeMillis()
        return (currentTime>(timeStamp+1800000))
    }
    fun getIconUrl() : String{
        return "https://openweathermap.org/img/wn/$iconId@2x.png"
    }
}
