package com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote

import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto.WeatherModelDto
import retrofit2.http.Query
import retrofit2.http.GET

interface WeatherApi {

    @GET("weather?appid=${Constant.API_KEY}")
    suspend fun getWeather(
        @Query(value = "lat") lat : Double,
        @Query(value = "lon") lon : Double
    ) : WeatherModelDto

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}