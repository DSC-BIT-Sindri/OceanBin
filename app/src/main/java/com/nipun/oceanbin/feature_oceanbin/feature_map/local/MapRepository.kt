package com.nipun.oceanbin.feature_oceanbin.feature_map.local

import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.Resource
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    fun getLatLong(query : String) : Flow<Resource<LatLng>>
}