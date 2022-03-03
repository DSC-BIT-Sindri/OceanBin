package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapModel

data class MapState(
    val mapProperties: MapProperties = MapProperties(
        maxZoomPreference = 20f, minZoomPreference = 2f
    ),
    val data : MapModel = MapModel()
) {
}