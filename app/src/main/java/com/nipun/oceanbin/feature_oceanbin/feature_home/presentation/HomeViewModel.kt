package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _weatherState = mutableStateOf(WeatherModel())
    val weatherState : State<WeatherModel> = _weatherState
}