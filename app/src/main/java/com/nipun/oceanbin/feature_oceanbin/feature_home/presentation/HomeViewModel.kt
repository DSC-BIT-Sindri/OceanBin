package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.HourlyState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val preferenceManager: PreferenceManager,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState
    private val _shouldShowRational = mutableStateOf(true)
    val shouldShowRational: State<Boolean> = _shouldShowRational

    private val _hourlyState = mutableStateOf(HourlyState())
    val hourlyState : State<HourlyState> = _hourlyState

    val hasPermission = preferenceManager.getBoolean(Constant.Has_Location, false)

    var count: Int = 0

    init {
        val key = preferenceManager.getBoolean(Constant.RATIONAL_KEY, true)
        _shouldShowRational.value = key
        count = preferenceManager.getInt(Constant.Count_Key, 0)
        _weatherState.value = WeatherState(data = preferenceManager.getWeather())
    }

    fun getLocation() {
        locationRepository.getLocation().onEach { result->
            when(result){
                is Resource.Loading -> {
                    _weatherState.value = WeatherState(
                        isLoading = true,
                        data = result.data?: weatherState.value.data
                    )
                }
                is Resource.Error -> {
                    _weatherState.value = WeatherState(
                        isLoading = false,
                        data = result.data?:weatherState.value.data,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _weatherState.value = WeatherState(
                        isLoading = false,
                        data = result.data?:weatherState.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
        locationRepository.getHourlyUpdated().onEach { result->
            when(result){
                is Resource.Loading -> {
                    _hourlyState.value = HourlyState(
                        isLoading = true,
                        data = result.data?.data?: hourlyState.value.data
                    )
                }
                is Resource.Error -> {
                    _hourlyState.value = HourlyState(
                        isLoading = false,
                        data = result.data?.data?:hourlyState.value.data,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _hourlyState.value = HourlyState(
                        isLoading = false,
                        data = result.data?.data?:hourlyState.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setPermanentlyDenied() {
        preferenceManager.saveBoolean(Constant.RATIONAL_KEY, false)
        _shouldShowRational.value = false
    }

    fun setHasPermission(value: Boolean = true) {
        preferenceManager.saveBoolean(Constant.Has_Location, value)
    }

    fun setCount() {
        preferenceManager.saveInteger(
            Constant.Count_Key,
            if (count == Constant.Max_Count) 0 else count + 1
        )
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}