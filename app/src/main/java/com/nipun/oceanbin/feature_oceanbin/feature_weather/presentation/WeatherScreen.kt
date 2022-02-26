package com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component.CurrentWeatherCard
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component.SmallWeatherCard
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component.SmallWeatherCardDetails
import com.nipun.oceanbin.ui.theme.*

@Composable
fun WeatherScreen(
    navController: NavController
) {
    WeatherScreenDetails()
}

@Preview
@Composable
fun WeatherScreenDetails(
    modifier: Modifier = Modifier
) {
    var temp = listOf<SmallWeatherCardDetails>(
        SmallWeatherCardDetails(
            imageId = R.drawable.ic_cloud_icon,
            percentage = 25,
            time = "12:00"
        ),
        SmallWeatherCardDetails(
            imageId = R.drawable.ic_cloud_icon,
            percentage = 25,
            time = "12:00"
        ),
        SmallWeatherCardDetails(
            imageId = R.drawable.ic_cloud_icon,
            percentage = 25,
            time = "12:00"
        ),
        SmallWeatherCardDetails(
            imageId = R.drawable.ic_cloud_icon,
            percentage = 25,
            time = "12:00"
        ),
    )

    Scaffold(backgroundColor = Color.LightGray) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .padding(top = BigSpacing)
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
                ){
                    item { 
                        CurrentWeatherCard(
                            date = "Feb 21, 2022",
                            day = "Sunday" ,
                            temperature = 25 ,
                            temperatureDetail = "Shiny Day",
                            location = "Bariatu, Ranchi",
                            imageId = R.drawable.ic_cloud_icon
                        )
                    }
                    
                    item {
                        DisplayWeatherRow(
                            modifier = modifier,
                            weatherType = "Hourly Weather",
                            weathers = temp
                        )
                    }
                    item{
                        Spacer(modifier = Modifier.size(MediumSpacing))
                    }
                    item{
                        DisplayWeatherRow(
                            modifier = modifier,
                            weatherType = "Daily Weather",
                            weathers = temp
                        )
                    }


                }
            }
        }
    }
}

@Composable
fun DisplayWeatherRow(
    modifier: Modifier = Modifier,
    weatherType : String,
    weathers : List<SmallWeatherCardDetails>
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(MediumSpacing)
        ) {
            Text(
                text = weatherType,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(weathers){ weather ->
                    SmallWeatherCard(
                        time = weather.time,
                        percentage = weather.percentage,
                        imageId = weather.imageId
                    )
                }
            }
        }
    }
}
