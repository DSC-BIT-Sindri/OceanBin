package com.nipun.oceanbin.feature_oceanbin.feature_home.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
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
    private val data = prefManager.getWeather()

    private fun hasPermission(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun getLocation(): Flow<Resource<WeatherDto>> = flow {
        val data = prefManager.getWeather()
        emit(
            Resource.Loading<WeatherDto>(
                data = data
            )
        )
        if (!hasPermission()) {
            emit(
                Resource.Error<WeatherDto>(
                    data = data,
                    message = "Location access not granted"
                )
            )
        } else {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val longitude = location?.longitude ?: 0.0
            val latitude = location?.latitude ?: 0.0
            if (data == null || data.isCallApi()) {
                try {
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
                    Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
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
    }
}