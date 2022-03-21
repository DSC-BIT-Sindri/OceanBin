package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nipun.oceanbin.core.firebase.User
import com.nipun.oceanbin.feature_oceanbin.feature_profile.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(){

    private val _user = mutableStateOf(User())
    val user : State<User> = _user

    init {
        _user.value = profileRepository.getUser()
    }

    fun logout(){
        profileRepository.logout()
    }
}