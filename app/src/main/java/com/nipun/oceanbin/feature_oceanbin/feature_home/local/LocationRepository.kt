package com.nipun.oceanbin.feature_oceanbin.feature_home.local

import com.nipun.oceanbin.core.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocation() : Flow<Resource<WeatherModel>>
}