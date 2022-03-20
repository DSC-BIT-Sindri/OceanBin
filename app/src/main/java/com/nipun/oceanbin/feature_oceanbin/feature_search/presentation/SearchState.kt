package com.nipun.oceanbin.feature_oceanbin.feature_search.presentation

import com.nipun.oceanbin.feature_oceanbin.feature_search.model.SearchResultModel

data class SearchState(
    val isLoading : Boolean = false,
    val data : List<SearchResultModel> = emptyList(),
    val error : String? = null
)
