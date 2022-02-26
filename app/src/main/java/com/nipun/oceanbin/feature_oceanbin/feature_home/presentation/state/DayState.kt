package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state

import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.DayModel

data class DayState(
    val isLoading : Boolean = false,
    val data : List<DayModel> = emptyList()
)
