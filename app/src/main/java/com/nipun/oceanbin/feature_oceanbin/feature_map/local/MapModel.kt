package com.nipun.oceanbin.feature_oceanbin.feature_map.local

import com.google.android.gms.maps.model.LatLng

data class MapModel(
    val latLang : LatLng? = null,
    val address : String = "",
    val addressLine : String = ""
)
