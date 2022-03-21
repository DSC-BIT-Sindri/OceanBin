package com.nipun.oceanbin.firsttime_display.feature_login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.core.firebase.FireStoreManager
import com.nipun.oceanbin.core.firebase.notValidEmail
import com.nipun.oceanbin.core.firebase.notValidPassword
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val fireStoreManager: FireStoreManager
) : ViewModel() {

    private val _email = mutableStateOf(TextState())
    private val _password = mutableStateOf(TextState())

    val email: State<TextState> = _email
    val password: State<TextState> = _password

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading

    fun changeValue(event: TextChangeEvent) {
        when (event) {
            is TextChangeEvent.Email -> {
                _email.value = TextState(text = event.name)
            }
            is TextChangeEvent.Password -> {
                _password.value = TextState(text = event.name)
            }
            else -> {
                return
            }
        }
    }

    fun signInUser() {
        val emailTemp = email.value.text
        val pass = password.value.text
        if (emailTemp.notValidEmail()) {
            _email.value = email.value.copy(error = "Email is not valid")
            return
        }
        if (pass.notValidPassword()) {
            _password.value = password.value.copy(error = "Password length should greater than 7")
            return
        }
        fireStoreManager.loginUser(
            emailTemp, pass
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
                        UIEvent.GoNext()
                    )
                    _showLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}