package com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.*
import java.security.AllPermission

@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    date: String,
    day: String,
    temperature: Int,
    temperatureDetail: String,
    location: String,
    imageId: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(MediumSpacing)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(BigSpacing),
            backgroundColor = TopbarLightBlue
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = modifier
                        .padding(top = BigSpacing, end = MediumSpacing)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = date,
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                    Text(
                        text = day,
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                }

                Column(
                    modifier = modifier
                        .padding(top = SmallSpacing)
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = "Weather image",
                        Modifier.size(WeatherImageSizeInWeatherScreen)
                    )
                    Text(
                        text = "$temperature ^C",
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 40.sp
                    )
                    Text(
                        text = temperatureDetail,
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "Your location is $location",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.size(SmallSpacing))
                        Text(
                            text = location,
                            fontFamily = RobotoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 24.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.size(BigSpacing))
                }
            }
        }
    }
}

@Composable
fun SmallWeatherCard(
    modifier: Modifier = Modifier,
    time: String,
    percentage: Int,
    imageId: Int
) {
    Card(
        modifier = modifier
            .border(
                MediumStroke,
                WeatherCardBorder,
                RoundedCornerShape(MediumSpacing)
            ),
        shape = RoundedCornerShape(MediumSpacing),
        backgroundColor = TopbarLightBlue,
    ) {
        Column(
            modifier = modifier
                .size(SmallWeatherCardSize)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "",
                modifier = modifier.size(50.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$percentage %",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = time,
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
            }
        }
    }
}