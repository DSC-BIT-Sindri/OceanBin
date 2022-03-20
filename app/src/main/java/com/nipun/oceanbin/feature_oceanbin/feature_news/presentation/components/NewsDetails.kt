package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components

data class NewsDetails(
    var newsId : Long = 0,
    var image : String = "",
    var heading : String = "",
    var description : String = "",
    var newsSource : String = ""
)
