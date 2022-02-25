package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto

data class HourlyForecastDto(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyContent>,
    val message: Int
){

}