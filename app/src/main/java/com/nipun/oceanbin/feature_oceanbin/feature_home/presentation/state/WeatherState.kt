package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.WeatherModel

data class WeatherState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val data : WeatherModel = WeatherModel()
)