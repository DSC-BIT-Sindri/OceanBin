package com.nipun.oceanbin.feature_oceanbin.feature_search.repository

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.toSearchModel
import com.nipun.oceanbin.feature_oceanbin.feature_search.model.SearchResultModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepository(
    context: Context
) {
    private val geocoder = Geocoder(context)

    //    private val placesClient = Places.createClient(context)
//    init {
//        Places.initialize(context,context.getString(R.string.API_KEY))
//    }

    fun getInitSearch(latLang:LatLng) : Flow<Resource<List<SearchResultModel>>> = flow{
        emit(Resource.Loading<List<SearchResultModel>>())
        try {
            val addressList = geocoder.getFromLocation(latLang.latitude,latLang.longitude, 10)
            Log.e("Search", "Address -> ${addressList.toString()}")
            emit(Resource.Success<List<SearchResultModel>>(
                data = addressList.map { it.toSearchModel() }
            ))
        } catch (e: Exception) {
            Log.e("Nipun", "SearchRepo -> ${e.message}")
            emit(
                Resource.Error<List<SearchResultModel>>(
                    message = "Something went wrong"
                )
            )
        }
    }
    fun getSearchResults(query: String): Flow<Resource<List<SearchResultModel>>> = flow {
        emit(Resource.Loading<List<SearchResultModel>>())
        kotlinx.coroutines.delay(800)
        Log.e("Search", "Inside Search Repo")
        try {
            val addressList = geocoder.getFromLocationName(query, 10)
            Log.e("Search", "Address -> ${addressList.toString()}")
            emit(Resource.Success<List<SearchResultModel>>(
                data = addressList.map { it.toSearchModel() }
            ))
        } catch (e: Exception) {
            Log.e("Nipun", "SearchRepo -> ${e.message}")
            emit(
                Resource.Error<List<SearchResultModel>>(
                    message = "Something went wrong"
                )
            )
        }
    }

//    fun autoComplete(query: String): Flow<Resource<List<AutoCompleteModel>>> = flow {
//        emit(Resource.Loading<List<AutoCompleteModel>>())
//        val token = AutocompleteSessionToken.newInstance()
//        val request = FindAutocompletePredictionsRequest.builder()
//            .setTypeFilter(TypeFilter.ADDRESS)
//            .setSessionToken(token)
//            .setQuery(query)
//            .build()
//        try {
//            val task = placesClient.findAutocompletePredictions(request).result
//            emit(Resource.Success<List<AutoCompleteModel>>(
//                data = task.autocompletePredictions.map { it.toAutocompletModel() }
//            ))
//        } catch (e: Exception) {
//            Log.e("Nipun", "SearchRepo -> ${e.message}")
//            emit(
//                Resource.Error<List<AutoCompleteModel>>(
//                    message = "Something went wrong"
//                )
//            )
//        }
//    }
}