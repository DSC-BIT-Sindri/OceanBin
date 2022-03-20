package com.nipun.oceanbin.feature_oceanbin.feature_search.model

import com.google.android.gms.maps.model.LatLng

data class SearchResultModel (
    val latLng : LatLng,
    val feature : String?,
    val locality : String?,
    val subAdmin : String?,
    val addressLine : String?,
    val admin : String?
){
    fun getName() : String{
        var res = ""
        res+=(locality?:feature?:"")+", "
        subAdmin?.let { res+="$it, " }
        admin?.let { res+="$it " }
        return res
    }
}