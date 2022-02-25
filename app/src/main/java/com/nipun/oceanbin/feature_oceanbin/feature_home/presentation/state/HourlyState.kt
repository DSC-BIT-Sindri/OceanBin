package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.HourlyModel

data class HourlyState(
    val isLoading : Boolean = false,
    val data : List<HourlyModel> = emptyList(),
    val error : String? = null
)
