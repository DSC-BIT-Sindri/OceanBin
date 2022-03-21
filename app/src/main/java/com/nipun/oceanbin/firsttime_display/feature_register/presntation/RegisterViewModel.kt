package com.nipun.oceanbin.firsttime_display.feature_register.presntation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.core.firebase.*
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStoreManager: FireStoreManager
) : ViewModel() {

    private val _email = mutableStateOf(TextState())
    private val _password = mutableStateOf(TextState())
    private val _name = mutableStateOf(TextState())
    private val _mobile = mutableStateOf(TextState())
    private val _confirmPassword = mutableStateOf(TextState())

    val email: State<TextState> = _email
    val phone: State<TextState> = _mobile
    val password: State<TextState> = _password
    val name: State<TextState> = _name
    val confirmPassword: State<TextState> = _confirmPassword

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _showLoading = mutableStateOf(false)
    val showLoading: State<Boolean> = _showLoading

    fun changeValue(event: TextChangeEvent) {
        when (event) {
            is TextChangeEvent.Email -> {
                _email.value = TextState(text = event.name)
            }
            is TextChangeEvent.Phone -> {
                _mobile.value = TextState(text = event.name)
            }
            is TextChangeEvent.Password -> {
                _password.value = TextState(text = event.name)
            }
            is TextChangeEvent.Name -> {
                _name.value = TextState(text = event.name)
            }
            is TextChangeEvent.ConfirmPassword -> {
                _confirmPassword.value = TextState(text = event.name)
            }
        }
    }

    fun createUser() {
        if (name.value.text.notValidName()) {
            _name.value = name.value.copy(error = "Name is too short")
            return
        }
        if (phone.value.text.notValidPhone()) {
            _mobile.value = phone.value.copy(error = "Phone number not valid")
            return
        }
        if (email.value.text.notValidEmail()) {
            _email.value = email.value.copy(error = "Email is not valid")
            return
        }
        if (password.value.text.notValidPassword()) {
            _password.value = password.value.copy(error = "Password length should greater than 7")
            return
        }
        if (confirmPassword.value.text.notMatchPassword(password.value.text)) {
            _confirmPassword.value =
                confirmPassword.value.copy(error = "Confirm password not matches with password")
            return
        }
        fireStoreManager.createUser(
            name = name.value.text,
            email = email.value.text,
            phone = phone.value.text,
            password = password.value.text
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
                    _eventFlow.emit(UIEvent.GoNext(
                        message = "verification email sent to ${email.value.text}"
                    ))
                    _showLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}