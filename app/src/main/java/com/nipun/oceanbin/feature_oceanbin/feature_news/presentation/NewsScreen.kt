package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsCard
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import com.nipun.oceanbin.ui.theme.*

@Composable
fun NewsScreen(
    navController: NavController
) {
    NewsScreenDetails(
        navController = navController,
        modifier = Modifier
    )
}

@Composable
fun NewsScreenDetails(
    navController: NavController,
    modifier : Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    newsViewModel.getNews()
    val newsList = newsViewModel.newsListState.value

    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .padding(bottom = DrawerHeight)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = modifier
                    .padding(top = BigSpacing, start = SmallSpacing, end = SmallSpacing)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LogoWithText(
                    modifier,
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
                Spacer(modifier = Modifier.size(MediumSpacing))

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = BigSpacing)
                ){
                    items(newsList){ news ->
                        NewsCard(
                            image = news.image,
                            heading = news.heading,
                            description = news.description,
                            newsSource = news.newsSource)
                        Spacer(modifier = Modifier.height(BigSpacing))
                    }
                }
            }
        }
    }
}