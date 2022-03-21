package com.nipun.oceanbin.feature_oceanbin.feature_profile.repository

import android.content.Context
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.firebase.User

class ProfileRepository (
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {

    fun getUser() : User{
        return preferenceManager.getUser()?: User()
    }
}