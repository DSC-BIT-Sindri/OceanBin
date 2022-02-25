package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

import java.io.Serializable

data class HourlyModel(
    val iconId : String,
    val temp : Int
) : Serializable{
    var time : String = ""
}