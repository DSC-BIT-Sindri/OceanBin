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
import androidx.navigation.NavController
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsCard
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import com.nipun.oceanbin.ui.theme.*

@Composable
fun NewsScreen(
    navController: NavController
) {
    NewsScreenDetails(modifier = Modifier)
}

@Preview
@Composable
fun NewsScreenDetails(
    modifier : Modifier = Modifier
) {
    var newsList = listOf<NewsDetails>(
        NewsDetails(
            image = R.drawable.test,
            heading = "Israeli man grows heaviest strawberry weighing 289 gms," +
                    "breaks world record",
            description = "Jus food in jars vegan genderless gingerbread figures no signs craft beer more celebrity than chef reservations at Dorsia. Turmeric icecream freegan a sneeky kebab campers Masterchef winners aren't chefs closed themed cafe. Fish tacos Paleo Pete's done it again the guy baking is really into the baking char siu glaze pork crackling surf and turf Portlandia. Another burger joint provenence let's eat a la carte field to fork let's have authentic street food three hat restaurant tatooed waiters Margeret River.",
            newsSource = "thetimesofindia.com"
        )
    )
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
                    }
                }
            }
        }
    }
}