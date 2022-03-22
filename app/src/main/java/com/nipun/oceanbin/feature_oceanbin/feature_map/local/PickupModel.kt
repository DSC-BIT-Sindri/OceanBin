package com.nipun.oceanbin.feature_oceanbin.feature_map.local

data class PickupModel(
    val time : String = "",
    val timeMillis : Long = 0L,
    val name : String = "",
    val email : String = "",
    val id : String = "",
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    val location : String = "",
    val shift : String = ""
)