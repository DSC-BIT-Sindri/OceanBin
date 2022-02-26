package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

import java.text.SimpleDateFormat
import java.util.*

data class DayModel(
    val timeStamp : Long,
    val iconId : String,
    val minTemp : Int,
    val maxTemp : Int
){
    fun getDay() : String{
        val sdf = SimpleDateFormat("MM/dd yyyy HH:mm:ss", Locale.ENGLISH)
        return sdf.format(timeStamp*1000L).substring(0,5)
    }
}
