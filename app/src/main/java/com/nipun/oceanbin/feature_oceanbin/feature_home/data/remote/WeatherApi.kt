package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote

import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall?appid=${Constant.API_KEY}&exclude=minutely,alerts&units=metric")
    suspend fun getWeather(
        @Query(value = "lat") lat : Double,
        @Query(value = "lon") lon : Double
    ) : WeatherDto

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}