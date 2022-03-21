package com.nipun.oceanbin.firsttime_display

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.firebase.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * This view model is main view model
 * This will execute when our app launched
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref : PreferenceManager
) :ViewModel(){
    private val _isInstalled = mutableStateOf(true)
    val isInstalled : State<Boolean> = _isInstalled

    private val _user = mutableStateOf(User())
    val user : State<User> = _user

    init {
        /*
         * This block will execute when our app is launched and get the value from shared preference
         * if value return true we'll gonna see the splash screen and if value will return false
         * we directly moved to home screen.
         */
        getInstallDetail()
    }
    private fun getInstallDetail(){
        viewModelScope.launch {
            _isInstalled.value = sharedPref.getBoolean()
            _user.value = sharedPref.getUser()?:User()
        }
    }

    fun setSplashViewed(){
        /*
         * This function is executed when our splash screen will be traversed completely
         * and user click continue button.
         * We save the first time installed value is false so that our app will not showing the
         * splash screen everytime our app will launch.
         */
        sharedPref.saveBoolean(value = false)
    }
}