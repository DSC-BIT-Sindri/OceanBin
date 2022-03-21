package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.core.firebase.User
import com.nipun.oceanbin.feature_oceanbin.feature_profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _user = mutableStateOf(User())
    val user: State<User> = _user

    private val _previousDenied = mutableStateOf(false)
    val previousDenied: State<Boolean> = _previousDenied

    private val _showPermissionDialogue = mutableStateOf(false)
    val showPermissionDialogue: State<Boolean> = _showPermissionDialogue

    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _user.value = profileRepository.getUser()
        _previousDenied.value = profileRepository.getPermanentlyDenied()
    }

    fun logout() {
        profileRepository.logout()
    }

    fun setPreviouslyDenied() {
        _previousDenied.value = true
        profileRepository.savePermanentlyDenied()
    }

    fun changeLocationPermissionDialogueValue(value: Boolean) {
        _showPermissionDialogue.value = value
    }

    fun getBitMap(uri: Uri) {
        _user.value = user.value.copy(image = uri.toString())
        profileRepository.updateImage(user.value, uri).onEach { result ->
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
                            result.data ?: "Image uploaded successfully"
                        )
                    )
                    _showLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}