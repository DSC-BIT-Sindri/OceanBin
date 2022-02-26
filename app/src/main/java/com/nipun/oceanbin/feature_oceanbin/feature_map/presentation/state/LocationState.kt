package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state

import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val isLoading : Boolean = false,
    val data : LatLng = LatLng(0.0,0.0),
    val message : String? = null
)