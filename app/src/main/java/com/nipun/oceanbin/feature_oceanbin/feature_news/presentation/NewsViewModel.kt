package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {
    val newsDb = Firebase.firestore.collection("News")

    private val _newsListState = mutableStateOf(mutableListOf<NewsDetails>())
    val newsListState : State<MutableList<NewsDetails>> = _newsListState

    fun getNews() : List<NewsDetails> {
        viewModelScope.launch {
            newsDb.get()
                .addOnSuccessListener { documents ->
                for (document in documents){
                    println("hello ${document.data}")
                    val news = document.toObject(NewsDetails::class.java)
                    newsListState.value.add(news)
                    _newsListState.value = newsListState.value
                }
            }.await()
        }
        return newsListState.value
    }

}