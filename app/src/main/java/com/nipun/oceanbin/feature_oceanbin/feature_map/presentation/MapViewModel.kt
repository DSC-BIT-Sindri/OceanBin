package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapRepository
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val mapRepository: MapRepository
) :ViewModel(){

    private val _location = mutableStateOf(LocationState())
    val location : State<LocationState> = _location

    init {
        _location.value = LocationState(
            isLoading = false,
            data = preferenceManager.getCurrentLocation()
        )
    }

    fun searchLocation(query : String){
        if(query.isEmpty())return
        mapRepository.getLatLong(query = query).onEach { result->
            when(result){
                is Resource.Success ->{
                    _location.value = LocationState(
                        isLoading = false,
                        data = result.data?:location.value.data
                    )
                }
                is Resource.Error ->{
                    _location.value = LocationState(
                        isLoading = false,
                        data = result.data?:location.value.data,
                        message = result.message
                    )
                }
                is Resource.Loading ->{
                    _location.value = LocationState(
                        isLoading = true,
                        data = result.data?:location.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}