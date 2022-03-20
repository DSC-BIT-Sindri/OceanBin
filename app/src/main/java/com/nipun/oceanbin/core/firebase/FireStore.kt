package com.nipun.oceanbin.core.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FireStoreManager(
    private val context: Context
) {

    private val preferenceManager = PreferenceManager(context)

    private val newsCollection = Firebase.firestore.collection("News")
    private val userCollection = Firebase.firestore.collection("Users")
    private val mAuth = FirebaseAuth.getInstance()

    fun getNews(): Flow<Resource<List<NewsDetails>>> = flow {
        emit(Resource.Loading<List<NewsDetails>>())
        try {
            val result = newsCollection.get().await().toObjects(NewsDetails::class.java)
            emit(
                Resource.Success<List<NewsDetails>>(
                    data = result
                )
            )
        } catch (e: Exception) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<List<NewsDetails>>(
                    message = "Something went wrong"
                )
            )
        }
    }

    fun createUser(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        try {
            mAuth.createUserWithEmailAndPassword(email, password).await().user?.uid?.let { userId ->
                val user = User(
                    id = userId,
                    name = name,
                    email = email,
                    phone = phone,
                    image = "null"
                )
                userCollection.add(user).await()
                preferenceManager.saveUser(value = user)
                preferenceManager.saveBoolean(value = false)
                emit(
                    Resource.Success<Boolean>(
                        data = true
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<Boolean>(
                    message = "Something went wrong"
                )
            )
        }
    }

    fun loginUser(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        try {
            mAuth.signInWithEmailAndPassword(email, password).await().user?.uid?.let { userId ->
                val userList = userCollection
                    .whereEqualTo("id", userId)
                    .get().await().toObjects(User::class.java)
                if(userList.isNotEmpty()) {
                    preferenceManager.saveUser(value = userList[0])
                    preferenceManager.saveBoolean(value = false)
                    emit(
                        Resource.Success<Boolean>(
                            data = true
                        )
                    )
                }else{
                    emit(Resource.Error<Boolean>(
                        message = "Cannot find user"
                    ))
                }
            }
        } catch (e: Exception) {
            Log.e("Nipun", e.message.toString())
            emit(
                Resource.Error<Boolean>(
                    message = "No user found with given details"
                )
            )
        }
    }
}

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = ""
)