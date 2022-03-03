package com.nipun.oceanbin.feature_oceanbin.feature_home.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.GpsProvider
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.WeatherApi
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto.WeatherDto
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

@SuppressLint("MissingPermission")
class LocationRepositoryImpl(
    private val context: Context,
    private val api: WeatherApi
) : LocationRepository {

    private val prefManager = PreferenceManager(context)

    override fun getLocation(): Flow<Resource<WeatherDto>> = flow {
        val data = prefManager.getWeather()
        emit(
            Resource.Loading<WeatherDto>(
                data = data
            )
        )
        val gpsProvider = GpsProvider(context)
        if (gpsProvider.canGetLocation) {
            gpsProvider.getLocation()
            gpsProvider.latLang?.let { latLang ->
                val longitude = latLang.longitude
                val latitude = latLang.latitude
                if (data == null || data.isCallApi()) {
                    try {
                        prefManager.saveCurrentLocation(value = LatLng(latitude, longitude))
                        val weatherDto = api.getWeather(latitude, longitude).apply {
                            timeStamp = System.currentTimeMillis()
                        }
                        prefManager.saveWeather(value = weatherDto)
                        emit(
                            Resource.Success<WeatherDto>(
                                data = weatherDto
                            )
                        )
                    } catch (e: HttpException) {
                        emit(
                            Resource.Error<WeatherDto>(
                                message = "Something went wrong",
                                data = data,
                            )
                        )
                    } catch (e: IOException) {
                        Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
                        Resource.Error<WeatherDto>(
                            message = "Couldn't reach server, check your internet connection.",
                            data = data,
                        )
                    } catch (e: Exception) {
                        Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
                        Resource.Error<WeatherDto>(
                            message = e.message.toString(),
                            data = data,
                        )
                    }
                }
            }
        } else {
            emit(
                Resource.Error<WeatherDto>(
                    data = data,
                    message = "Location access not granted"
                )
            )
        }
    }
}