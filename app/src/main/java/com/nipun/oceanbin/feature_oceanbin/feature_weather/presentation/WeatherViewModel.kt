package com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.getTimeInString
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.DayState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.HourlyState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val _hourlyState = mutableStateOf(HourlyState())
    val hourlyState: State<HourlyState> = _hourlyState

    private val _dayState = mutableStateOf(DayState())
    val dayState: State<DayState> = _dayState

    init {
        getLocation()
    }

    private fun getLocation(){
        locationRepository.getLocation().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    result.data?.let { data ->
                        _weatherState.value = WeatherState(
                            isLoading = true,
                            data = data.current.toWeatherModel().apply {
                                location = preferenceManager.getAddress(
                                    longitude = data.lon,
                                    latitude = data.lat
                                )
                            }
                        )
                        _hourlyState.value = HourlyState(
                            isLoading = true,
                            data = data.hourly.filter { hourly ->
                                (hourly.dt * 1000L) >= (System.currentTimeMillis() - 3600000L)
                            }.filterIndexed { index, _ ->
                                index <= 24 && index % 2 == 0
                            }.mapIndexed { index, hourData ->
                                hourData.toHourlyModel().apply {
                                    time = if (index == 0) "Now" else hourData.dt.toInt()
                                        .getTimeInString()
                                }
                            }
                        )
                        _dayState.value = DayState(
                            isLoading = true,
                            data = data.daily.filterIndexed { index,_ ->
                                index<7
                            }.map {
                                it.toDailyModel()
                            }
                        )
                    }
                }
                is Resource.Error -> {
                    result.data?.let { data ->
                        _weatherState.value = WeatherState(
                            isLoading = false,
                            data = data.current.toWeatherModel().apply {
                                location = preferenceManager.getAddress(
                                    longitude = data.lon,
                                    latitude = data.lat
                                )
                            },
                            error = result.message
                        )
                        _hourlyState.value = HourlyState(
                            isLoading = true,
                            data = data.hourly.filter { hourly ->
                                (hourly.dt * 1000L) >= (System.currentTimeMillis() - 3600000L)
                            }.filterIndexed { index, _ ->
                                index <= 24 && index % 2 == 0
                            }.mapIndexed { index, hourData ->
                                hourData.toHourlyModel().apply {
                                    time = if (index == 0) "Now" else hourData.dt.toInt()
                                        .getTimeInString()
                                }
                            }
                        )
                        _dayState.value = DayState(
                            isLoading = false,
                            data = data.daily.filterIndexed { index,_ ->
                                index<7
                            }.map {
                                it.toDailyModel()
                            }
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.let { data ->
                        _weatherState.value = WeatherState(
                            isLoading = false,
                            data = data.current.toWeatherModel().apply {
                                location = preferenceManager.getAddress(
                                    longitude = data.lon,
                                    latitude = data.lat
                                )
                            }
                        )
                        _hourlyState.value = HourlyState(
                            isLoading = true,
                            data = data.hourly.filter { hourly ->
                                (hourly.dt * 1000L) >= (System.currentTimeMillis() - 3600000L)
                            }.filterIndexed { index, _ ->
                                index <= 24 && index % 2 == 0
                            }.mapIndexed { index, hourData ->
                                hourData.toHourlyModel().apply {
                                    time = if (index == 0) "Now" else hourData.dt.toInt()
                                        .getTimeInString()
                                }
                            }
                        )
                        _dayState.value = DayState(
                            isLoading = false,
                            data = data.daily.filterIndexed { index,_ ->
                                index<7
                            }.map {
                                it.toDailyModel()
                            }
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}