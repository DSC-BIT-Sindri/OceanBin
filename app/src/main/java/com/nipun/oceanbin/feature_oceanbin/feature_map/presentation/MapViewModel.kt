package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapModel
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapRepository
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state.LocationState
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state.MapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val mapRepository: MapRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location

    private val _currentLocation = mutableStateOf(MapModel())
    val currentLocation : State<MapModel> = _currentLocation

    init {
        initLocation()
        savedStateHandle.get<String>(Constant.LAT)?.let { lat->
            savedStateHandle.get<String>(Constant.LON)?.let { lon->
                try {
                    Log.e("Search","Lon -> $lon \n lat -> $lat")
                    val latitude = lat.toDouble()
                    val longitude = lon.toDouble()
                    val mapModel = MapModel(
                        latLang = LatLng(latitude,longitude),
                        address = preferenceManager.getAddress(longitude = longitude, latitude = latitude)
                    )
                    _location.value = LocationState(
                        isLoading = false,
                        data = mapModel
                    )
                }catch (e : Exception){
                    Log.e("Search","Error -> ${e.message}")
                }
            }
        }
    }

    private fun initLocation() {
        mapRepository.getInitLocation().onEach { result ->
            when (result) {
                is Resource.Success -> {
                   _currentLocation.value = result.data?: MapModel()
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

}