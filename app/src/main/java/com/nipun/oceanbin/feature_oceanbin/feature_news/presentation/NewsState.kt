package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation

import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails

data class NewsState(
    val isLoading : Boolean = false,
    val data : List<NewsDetails> = emptyList(),
    val error : String? = null
) {
}