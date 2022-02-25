package com.nipun.oceanbin.feature_oceanbin.feature_home.local.models

data class HourlyDataModel(
    var data : List<HourlyModel>,
    var timeStamp : Long = 0L
){
    fun isCallApi() : Boolean{
        val currentTime = System.currentTimeMillis()
        return (currentTime>(timeStamp+86400000))
    }
}
