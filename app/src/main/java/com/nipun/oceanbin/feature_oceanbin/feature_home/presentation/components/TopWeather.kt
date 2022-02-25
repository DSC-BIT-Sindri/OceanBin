package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TopWeather(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
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
                .padding(SmallSpacing)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cloud_icon),
                contentDescription = "Cloud Image",
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f)
                    .align(Alignment.TopEnd),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
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
    }
}