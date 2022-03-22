package com.nipun.oceanbin.feature_oceanbin.feature_profile.repository

import android.content.Context
import android.net.Uri
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.firebase.FireStoreManager
import com.nipun.oceanbin.core.firebase.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepository(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) {

    private val fireStoreManager = FireStoreManager(context)

    fun getUser(): User {
        return preferenceManager.getUser() ?: User()
    }

    fun logout() {
        preferenceManager.deleteUser()
    }

    fun savePermanentlyDenied() {
        preferenceManager.saveBoolean(PreferenceManager.READ_PREVIOUS_DENIED, true)
    }

    fun getPermanentlyDenied() : Boolean {
        return preferenceManager.getBoolean(PreferenceManager.READ_PREVIOUS_DENIED, false)
    }

    fun updateImage(user : User,uri : Uri) : Flow<Resource<String>> {
        return fireStoreManager.uploadImage(user,uri)
    }

    fun updateUser(user: User): Flow<Resource<String>> {
        return fireStoreManager.updateUser(user)
    }
}