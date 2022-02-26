package com.nipun.oceanbin.feature_oceanbin.feature_home.local

import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_home.data.remote.dto.WeatherDto
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocation() : Flow<Resource<WeatherDto>>
}