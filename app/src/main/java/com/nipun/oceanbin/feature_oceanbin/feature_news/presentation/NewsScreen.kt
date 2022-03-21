package com.nipun.oceanbin.feature_oceanbin.feature_news.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsCard
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
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val newsState = newsViewModel.newsState.value
    val newsList = newsState.data

    Scaffold(backgroundColor = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .padding(bottom = DrawerHeight)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing, start = SmallSpacing, end = SmallSpacing)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                ) {
                    items(newsList) { news ->
                        NewsCard(
                            image = news.image,
                            heading = news.heading,
                            description = news.description,
                            newsSource = news.newsSource,
                            time = news.time
                        )
                        Spacer(modifier = Modifier.height(BigSpacing))
                    }
                }
            }
            if (newsState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}