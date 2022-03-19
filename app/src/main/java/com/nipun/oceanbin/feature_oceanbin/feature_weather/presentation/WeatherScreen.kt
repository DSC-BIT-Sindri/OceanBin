package com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component.CurrentWeatherCard
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component.SmallWeatherCard
import com.nipun.oceanbin.feature_oceanbin.feature_weather.models.SmallWeatherCardDetails
import com.nipun.oceanbin.feature_oceanbin.feature_weather.models.WeatherInfoWithType
import com.nipun.oceanbin.ui.theme.*

@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val dayModel = viewModel.dayState.value.data
    val hourModel = viewModel.hourlyState.value.data
    val weatherModel = viewModel.weatherState.value.data

    val weatherInfos = listOf(
        WeatherInfoWithType(
            weatherType = "Hourly Weather",
            weathers = hourModel.map { it.toSmallWeatherCardDetails() }
        ),
        WeatherInfoWithType(
            weatherType = "Daily Weather",
            weathers = dayModel.mapIndexed { index, dayModel ->
                dayModel.toSmallWeatherCardDetail(index)
            }
        )
    )
    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Box(
            modifier = Modifier
                .padding(bottom = DrawerHeight)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LogoWithText(
                    Modifier,
                    color1 = MainBg,
                    color2 = MainBg
                )
                Spacer(modifier = Modifier.size(MediumSpacing))
                CurrentWeatherCard(
                    date = weatherModel.getCurrentDate(),
                    day = weatherModel.getCurrentDay(),
                    temperature = weatherModel.temperature,
                    temperatureDetail = weatherModel.weather,
                    location = weatherModel.location,
                    imageId = R.drawable.ic_cloud_icon,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(MediumSpacing))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = MediumSpacing,
                                topEnd = MediumSpacing
                            )
                        )
                        .background(WhiteShade)
                ) {
                    items(weatherInfos.size) { index ->
                        val weather = weatherInfos[index]
                        if (index > 0) {
                            Spacer(modifier = Modifier.size(MediumSpacing))
                        }
                        DisplayWeatherRow(
                            weatherType =weather.weatherType,
                            weathers =weather.weathers,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.White)
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
    weatherType: String,
    weathers: List<SmallWeatherCardDetails>
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
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(weathers) { weather ->
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
