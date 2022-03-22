package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.DropDownData
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapModel
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.MapRepository
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val mapRepository: MapRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _location = mutableStateOf(LocationState())
    val location: State<LocationState> = _location


    @RequiresApi(Build.VERSION_CODES.O)
    private val _pickupDate = mutableStateOf(DropDownData())

    @RequiresApi(Build.VERSION_CODES.O)
    val pickupDate: State<DropDownData> = _pickupDate

    private val _currentLocation = mutableStateOf(MapModel())
    val currentLocation: State<MapModel> = _currentLocation

    private val _dateIndex = mutableStateOf(0)
    val dateIndex: State<Int> = _dateIndex

    private val _timeIndex = mutableStateOf(0)
    val timeIndex: State<Int> = _timeIndex

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading

    init {
        initLocation()
        savedStateHandle.get<String>(Constant.LAT)?.let { lat ->
            savedStateHandle.get<String>(Constant.LON)?.let { lon ->
                try {
                    Log.e("Search", "Lon -> $lon \n lat -> $lat")
                    val latitude = lat.toDouble()
                    val longitude = lon.toDouble()
                    val mapModel = MapModel(
                        latLang = LatLng(latitude, longitude),
                        address = preferenceManager.getAddress(
                            longitude = longitude,
                            latitude = latitude
                        ),
                        addressLine = preferenceManager.getAddress(
                            longitude = longitude,
                            latitude = latitude,
                            isLine = true
                        )
                    )
                    _location.value = LocationState(
                        isLoading = false,
                        data = mapModel
                    )
                } catch (e: Exception) {
                    Log.e("Search", "Error -> ${e.message}")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changePickupDate(localDate: LocalDate, index: Int) {
        _pickupDate.value = pickupDate.value.copy(localDate = localDate)
        _dateIndex.value = index
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changePickupTime(timeMillis: Long,shift : String, index: Int) {
        _pickupDate.value = pickupDate.value.copy(timeMillis = timeMillis, time = shift )
        _timeIndex.value = index
    }


    private fun initLocation() {
        mapRepository.getInitLocation().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _currentLocation.value = result.data ?: MapModel()
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestSchedule() {
        viewModelScope.launch {
            when{
                pickupDate.value == DropDownData() -> {
                    _eventFlow.emit(
                        UIEvent.ShowSnackbar(
                            message = "Pick date and time"
                        )
                    )
                }
                timeIndex.value == 0 -> {
                    _eventFlow.emit(
                        UIEvent.ShowSnackbar(
                            message = "Pick time"
                        )
                    )
                }
                dateIndex.value == 0 -> {
                    _eventFlow.emit(
                        UIEvent.ShowSnackbar(
                            message = "Pick date "
                        )
                    )
                }
                else -> {
                    mapRepository.setPickupDate(
                        pickupDate.value.copy(
                            latitude = currentLocation.value.latLang?.latitude ?: 0.0,
                            longitude = currentLocation.value.latLang?.longitude ?: 0.0,
                            location = currentLocation.value.addressLine
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _showLoading.value = true
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UIEvent.ShowSnackbar(
                                        result.message ?: "Unknown Error"
                                    )
                                )
                                _showLoading.value = false
                            }
                            is Resource.Success -> {
                                _eventFlow.emit(
                                    UIEvent.ShowSnackbar(
                                        message = result.data ?: "Pickup Scheduled"
                                    )
                                )
                                _showLoading.value = false
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }
}