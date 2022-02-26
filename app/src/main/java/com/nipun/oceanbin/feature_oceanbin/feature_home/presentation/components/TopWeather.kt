package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.DayModel
import com.nipun.oceanbin.feature_oceanbin.feature_home.local.models.HourlyModel
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.DayState
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.state.HourlyState
import com.nipun.oceanbin.ui.theme.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TopWeather(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    offset : Float,
) {
    val weatherState = homeViewModel.weatherState.value
    val weatherInfo = weatherState.data
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(SmallSpacing))
        LogoWithText(
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(SmallSpacing))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SmallSpacing)
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .componentRegistry {
                    add(SvgDecoder(LocalContext.current))
                }
                .build()
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                Image(
                    painter = rememberImagePainter(
                        data = weatherInfo.iconId,
                        builder = {
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.ic_cloud_icon)
                        }
                    ),
                    contentDescription = "Cloud Image",
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                        .align(Alignment.TopEnd),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(start = SmallSpacing),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location",
                        modifier = Modifier.size(BigSpacing),
                        tint = MainBg
                    )
                    Spacer(modifier = Modifier.width(SmallSpacing))
                    Text(
                        text = weatherInfo.location,
                        style = MaterialTheme.typography.h3,
                        color = MainBg
                    )
                }
                Spacer(modifier = Modifier.height(MediumSpacing))
                Text(
                    text = "${weatherInfo.temperature} \u2103",
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier.height(ExtraSmallSpacing))
                Text(
                    text = weatherInfo.weather,
                    style = MaterialTheme.typography.h2,
                    color = MainBg
                )
                Spacer(modifier = Modifier.height(ExtraSmallSpacing))
                Text(
                    text = weatherInfo.getCurrentDate(),
                    style = MaterialTheme.typography.h3,
                    color = MainBg
                )
            }
        }
        Spacer(modifier = Modifier.size(IconSize))
        HourlyComp(
            hourlyState = homeViewModel.hourlyState.value,
            dailyState = homeViewModel.dayState.value,
            modifier = Modifier.fillMaxSize(),
            offset = offset
        )
    }
}

@Composable
fun HourlyComp(
    modifier: Modifier = Modifier,
    hourlyState: HourlyState,
    dailyState: DayState,
    offset: Float
) {
    val hourlyInfo = hourlyState.data
    val dayValue = dailyState.data
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = offset>=0.2f)
    ) {
        LazyRow {
            items(hourlyInfo) { hourlyModel: HourlyModel ->
                SingleWeatherCard(
                    hourlyModel = hourlyModel,
                    modifier = Modifier
                        .padding(
                            start = MediumSpacing,
                            end = SmallSpacing
                        )
                )
            }
        }
        Spacer(modifier = Modifier.size(BigSpacing))
        HorizontalLine()
        Spacer(modifier = Modifier.size(SmallSpacing))
        dayValue.forEachIndexed { index, dayModel ->
            if (index > 0) Spacer(modifier = Modifier.size(SmallSpacing))
            SingleDayCard(
                dayModel = dayModel, index = index,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SmallSpacing)
            )
        }
    }
}

@Composable
fun SingleDayCard(
    dayModel: DayModel,
    index: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (index == 0) "Today" else dayModel.getDay(),
            style = MaterialTheme.typography.body1,
            color = MainBg
        )
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .componentRegistry {
                add(SvgDecoder(LocalContext.current))
            }
            .build()
        CompositionLocalProvider(LocalImageLoader provides imageLoader) {
            Image(
                painter = rememberImagePainter(
                    data = dayModel.iconId,
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                    },
                ),
                contentDescription = "Cloud Image",
                modifier = Modifier
                    .height(DrawerHeight)
                    .size(IconSize),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayModel.minTemp.toString(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Thin,
                fontFamily = RobotoFamily,
                color = MainBg
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            Text(
                text = dayModel.maxTemp.toString(),
                style = MaterialTheme.typography.body1,
                color = MainBg
            )
        }
    }
}

@Composable
fun SingleWeatherCard(
    modifier: Modifier = Modifier,
    hourlyModel: HourlyModel
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(SmallSpacing),
        color = LightBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
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
                        data = hourlyModel.iconId,
                        builder = {
                            transformations(CircleCropTransformation())
                            crossfade(true)
                        },
                    ),
                    contentDescription = "Cloud Image",
                    modifier = Modifier
                        .height(DrawerHeight)
                        .size(IconSize),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
            Text(
                text = "${hourlyModel.temp} \u2103",
                style = MaterialTheme.typography.body1,
                color = MainBg
            )
            Spacer(modifier = Modifier.height(ExtraSmallSpacing))
            Text(
                text = hourlyModel.time,
                style = MaterialTheme.typography.h3,
                color = MainBg
            )
        }
    }
}

@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = ExtraBigSpacing)
    ) {
        drawLine(
            color = LightBgShade,
            start = Offset(0f, 0f),
            end = Offset(this.size.width * 1f, 0f),
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
    }
}