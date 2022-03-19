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
    var iconId: String = "",
    val sunRise: String = "04:00",
    val sunSet: String = "17:30"
) {
    fun getCurrentDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatTime = currentTime.format(formatter).substring(0, 12)
           if (formatTime.get(formatTime.lastIndex) == ',') formatTime.substring(
                0,
                formatTime.lastIndex
            ) else formatTime
        } else {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        }
    }

    fun getCurrentDay(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().dayOfWeek.name
        } else {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            return sdf.format(d)
        }
    }

}
