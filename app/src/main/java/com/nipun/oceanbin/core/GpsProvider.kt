package com.nipun.oceanbin.core

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng

class GpsProvider(private val context: Context)  {

    var canGetLocation = false

    private val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val minDistance = 10.0f
    private val minTime = 1000*60*1L

    init {
        canGetLocation()
    }

    private fun canGetLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.forEach { permission ->
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    canGetLocation = false
                    return
                }
            }
        }
        canGetLocation = true
    }

    var latLang : LatLng? = null

    @SuppressLint("MissingPermission")
    fun getLocation()  {
        if(!canGetLocation) return
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isGpsEnable && !isNetworkEnable) return
            if(isGpsEnable){
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                location?.let { lc->
                    latLang = LatLng(
                        lc.latitude,lc.longitude
                    )
                }
                return
            }
            if(isNetworkEnable){
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                location?.let { lc->
                    latLang = LatLng(
                        lc.latitude,lc.longitude
                    )
                }
            }
        }catch (e : Exception){
            return
        }
    }

}