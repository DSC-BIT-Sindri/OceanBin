package com.nipun.oceanbin.feature_oceanbin.feature_weather.models

import com.nipun.oceanbin.feature_oceanbin.feature_weather.models.SmallWeatherCardDetails

data class WeatherInfoWithType(
    val weatherType : String,
    val weathers : List<SmallWeatherCardDetails>
)
