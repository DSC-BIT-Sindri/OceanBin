package com.nipun.oceanbin.feature_oceanbin.feature_home.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import androidx.compose.ui.text.capitalize
import androidx.core.app.ActivityCompat
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.WeatherApi
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class LocationRepositoryImpl(
    private val context: Context,
    private val api: WeatherApi
) : LocationRepository {

    override fun getLocation(): Flow<Resource<WeatherModel>> = flow {
        val prefManager = PreferenceManager(context)
        val data = prefManager.getWeather()
        emit(
            Resource.Loading<WeatherModel>(
                data = data
            )
        )
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            emit(
                Resource.Error<WeatherModel>(
                    data = data,
                    message = "Location access not granted"
                )
            )
        }
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val longitude = location?.longitude ?: 0.0
        val latitude = location?.latitude ?: 0.0
        if (data.isCallApi(longitude, latitude)) {
            try {
                Log.e("Nipun","Inside api call")
                val weatherDto = api.getWeather(latitude, longitude)
                data.apply {
                    this.location = getAddress(longitude, latitude)
                    temperature = (weatherDto.main.temp - 273).toInt()
                    weather = weatherDto.weather[0].description
                    timeStamp = System.currentTimeMillis()
                    lat = latitude
                    lon = longitude
                }
                prefManager.saveWeather(value = data)
                emit(
                    Resource.Success<WeatherModel>(
                        data = data
                    )
                )
            } catch (e: HttpException) {
                Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
                emit(
                    Resource.Error<WeatherModel>(
                        message = "Something went wrong",
                        data = data,
                    )
                )
            } catch (e: IOException) {
                Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
                Resource.Error<WeatherModel>(
                    message = "Couldn't reach server, check your internet connection.",
                    data = data,
                )
            } catch (e: Exception) {
                Log.e("NipunL", "WeatherInfo -> ${e.message.toString()}")
                Resource.Error<WeatherModel>(
                    message = e.message.toString(),
                    data = data,
                )
            }
        }
    }

    private fun getAddress(longitude: Double, latitude: Double): String {
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addressList = geoCoder.getFromLocation(latitude, longitude, 1)
        if (addressList.isNullOrEmpty()) return ""
        else {
            val address: Address = addressList[0]
            var res = ""
            for (i in 0 until address.maxAddressLineIndex) {
                res += address.getAddressLine(i) + "\n"
            }
            res += address.locality.capitalize(Locale.getDefault()) + ", " + address.subAdminArea.capitalize(
                Locale.getDefault()
            )
            return res
        }
    }
}