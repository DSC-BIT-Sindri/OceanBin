package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.firebase.FireStoreManager
import com.nipun.oceanbin.core.getTimeInString
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.LocationRepository
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.DayState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.HourlyState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.WeatherState
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val preferenceManager: PreferenceManager,
    private val locationRepository: LocationRepository,
    private val fireStoreManager: FireStoreManager
) : ViewModel() {

    private val _shouldShowRational = mutableStateOf(true)
    val shouldShowRational: State<Boolean> = _shouldShowRational

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val _hourlyState = mutableStateOf(HourlyState())
    val hourlyState: State<HourlyState> = _hourlyState

    private val _dayState = mutableStateOf(DayState())
    val dayState: State<DayState> = _dayState

    val hasPermission = preferenceManager.getBoolean(Constant.Has_Location, false)

    var count: Int = 0

    private val _newsState = mutableStateOf(NewsState())
    val newsState : State<NewsState> = _newsState

    init {
        val key = preferenceManager.getBoolean(Constant.RATIONAL_KEY, true)
        _shouldShowRational.value = key
        count = preferenceManager.getInt(Constant.Count_Key, 0)
        getNews()
    }

    private fun getNews()  {
        fireStoreManager.getNews().onEach { result->
            when(result){
                is Resource.Loading -> {
                    _newsState.value = NewsState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _newsState.value = NewsState(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _newsState.value = NewsState(
                        isLoading = false,
                        data = result.data?:newsState.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getLocation() {
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