package com.nipun.oceanbin.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref : PreferenceManager
) :ViewModel(){
    private val _isInstalled = mutableStateOf(true)
    val isInstalled : State<Boolean> = _isInstalled

    init {
        getInstallDetail()
    }
    private fun getInstallDetail(){
        viewModelScope.launch {
            _isInstalled.value = sharedPref.getBoolean()
        }
    }

    fun setSplashViewed(){
        sharedPref.saveBoolean(value = false)
    }
}