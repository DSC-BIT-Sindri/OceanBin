package com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.*

@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    date: String,
    day: String,
    temperature: Int,
    temperatureDetail: String,
    location: String,
    imageId: String
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(top = BigSpacing, end = MediumSpacing)
                .align(Alignment.TopEnd)
        ) {
            Text(
                text = date,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = MainBg
            )
            Text(
                text = day,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = MainBg
            )

        }

        Column(
            modifier = modifier
                .padding(top = SmallSpacing)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .componentRegistry {
                    add(SvgDecoder(LocalContext.current))
                }
                .build()
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                Image(
                    painter = rememberImagePainter(
                        data = imageId,
                        builder = {
                            transformations(CircleCropTransformation())
                            crossfade(true)
                            placeholder(R.drawable.ic_cloud_icon)
                        },
                    ),
                    contentDescription = "Cloud Image",
                    modifier = Modifier
                        .size(WeatherImageSizeInWeatherScreen),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
            Text(
                text = "$temperature ℃",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = MainBg
            )
            Text(
                text = temperatureDetail,
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MainBg
            )
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Your location is $location",
                    tint = MainBg
                )
                Spacer(modifier = Modifier.size(SmallSpacing))
                Text(
                    text = location,
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    color = MainBg
                )
            }
            Spacer(modifier = Modifier.size(BigSpacing))
        }
    }
}


@Composable
fun SmallWeatherCard(
    modifier: Modifier = Modifier,
    time: String,
    percentage: Int,
    imageId: String
) {
    Column(
        modifier = modifier
            .size(SmallWeatherCardSize)
            .padding(SmallSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .componentRegistry {
                add(SvgDecoder(LocalContext.current))
            }
            .build()
        CompositionLocalProvider(LocalImageLoader provides imageLoader) {
            Image(
                painter = rememberImagePainter(
                    data = imageId,
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                    },
                ),
                contentDescription = "Cloud Image",
                modifier = Modifier
                    .size(DrawerHeight),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_humdity),
                contentDescription = "Humidity",
                modifier = Modifier
                    .size(BigSpacing)
                    .padding(ExtraSmallSpacing),
                tint = Color.Black
            )
            Text(
                text = "$percentage %",
                style = MaterialTheme.typography.overline,
                fontWeight = FontWeight.Normal
            )
        }
        Text(
            text = time,
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Normal
        )
    }
}