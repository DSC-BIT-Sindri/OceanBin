package com.nipun.oceanbin.feature_oceanbin.feature_map.local

import com.nipun.oceanbin.core.Resource
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    fun getInitLocation() : Flow<Resource<MapModel>>

    fun setPickupDate(pickupDate : DropDownData) : Flow<Resource<String>>
}