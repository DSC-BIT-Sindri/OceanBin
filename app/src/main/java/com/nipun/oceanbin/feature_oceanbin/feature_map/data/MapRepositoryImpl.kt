package com.nipun.oceanbin.feature_oceanbin.feature_map.data

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.*
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.DropDownData
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapModel
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapRepository
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.PickupModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MapRepositoryImpl(
    private val context: Context
) : MapRepository {
    private val geocoder = Geocoder(context)
    private val prefManager = PreferenceManager(context)

    private val pickupRef = Firebase.firestore.collection("PickupSchedule")

    override fun getInitLocation(): Flow<Resource<MapModel>> = flow {
        emit(Resource.Loading<MapModel>(data = null))
        val gpsProvider = GpsProvider(context)
        if (gpsProvider.canGetLocation) {
            gpsProvider.getLocation()
            val latLng = gpsProvider.latLang!!
            emit(
                Resource.Success<MapModel>(
                    data = MapModel(
                        latLng,
                        prefManager.getAddress(
                            longitude = latLng.longitude,
                            latitude = latLng.latitude
                        ),
                        prefManager.getAddress(
                            longitude = latLng.longitude,
                            latitude = latLng.latitude,
                            isLine = true
                        )
                    )
                )
            )
        } else {
            emit(
                Resource.Error<MapModel>(
                    message = "Location service is disabled"
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setPickupDate(pickupDate: DropDownData): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())
        try {
            val usr = prefManager.getUser()
            usr?.let { user ->
                val pickupModel = PickupModel(
                    time = pickupDate.getTimeStamp().toTimeWithDate(),
                    timeMillis = pickupDate.getTimeStamp(),
                    name = user.name,
                    email = user.email,
                    id = user.id,
                    location = pickupDate.location,
                    latitude = pickupDate.latitude,
                    longitude = pickupDate.longitude,
                    shift = pickupDate.time
                )
                pickupRef.document(user.id).set(pickupModel).await()
                emit(
                    Resource.Success<String>(
                        data = context.getString(R.string.pickup_vehicle_scheduled)
                    )
                )
            } ?: emit(
                Resource.Success<String>(
                    data = context.getString(R.string.no_login)
                )
            )
        } catch (e: Exception) {
            Log.e("Pickup","Error -> ${e.message}")
            emit(
                Resource.Error<String>(
                    message = context.getString(R.string.something_went_wrong)
                )
            )
        }
    }
}