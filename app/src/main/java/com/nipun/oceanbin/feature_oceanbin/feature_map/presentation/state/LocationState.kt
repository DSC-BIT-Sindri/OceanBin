package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state

import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapModel

data class LocationState(
    val isLoading : Boolean = false,
    val data : MapModel = MapModel(),
    val message : String? = null
)