package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.firebase.FireStoreManager
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fireStoreManager: FireStoreManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _newsState = mutableStateOf(NewsState())
    val newsState: State<NewsState> = _newsState

    init {
        savedStateHandle.get<String>(Constant.NEWS)?.let { param ->
            val gson = Gson()
            val strNews = URLDecoder.decode(param, "utf-8")
            val news = gson.fromJson(strNews, NewsDetails::class.java)
            _newsState.value = NewsState(
                data = listOf(news)
            )
        } ?: getNews()
    }

    private fun getNews() {
        fireStoreManager.getNews().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _newsState.value = NewsState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _newsState.value = NewsState(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Success -> {
                    _newsState.value = NewsState(
                        isLoading = false,
                        data = result.data ?: newsState.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}