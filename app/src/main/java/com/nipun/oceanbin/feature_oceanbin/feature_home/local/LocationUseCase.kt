package com.nipun.oceanbin.feature_oceanbin.feature_home.local

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import java.util.*
import javax.inject.Inject

class LocationUseCase (
    private val locationRepository: LocationRepository,
    private val context : Context
) {


}