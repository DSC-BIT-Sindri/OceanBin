package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.openSettings
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.LightBg
import com.nipun.oceanbin.ui.theme.MainBg
import com.nipun.oceanbin.ui.theme.MediumSpacing
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    if (!homeViewModel.hasPermission && homeViewModel.count == Constant.Max_Count) {
        homeViewModel.setCount()
        EnableLocationBySetting()
    }
    val minOffSet = 0.13f
    val maxOffset = 0.9f
    val scrollOffset = 20f
    var sizeState by remember {
        mutableStateOf(minOffSet)
    }
    val size by animateFloatAsState(
        targetValue = sizeState,
        tween(
            durationMillis = 500,
            delayMillis = 0
        )
    )
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true){
        sizeState = 0.25f
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    val y = pan.y
                    coroutineScope.launch {
                        if (y >= scrollOffset) {
                            sizeState = maxOffset
                        } else if (y <= -scrollOffset) {
                            sizeState = minOffSet
                        }
                    }
                }
            }
    ) {
        TopWeather(
            modifier = Modifier
                .background(LightBg)
                .padding(
                    start = MediumSpacing,
                    end = MediumSpacing,
                    top = MediumSpacing
                )
                .fillMaxWidth()
                .fillMaxHeight(size),
            homeViewModel = homeViewModel
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
        ){
            BoxContents(
                modifier = Modifier
                    .fillMaxSize(),
                homeViewModel = homeViewModel,
                navController = navController,
            )
        }
    }
}

@Composable
fun EnableLocationBySetting() {
    val context = LocalContext.current
    val openDialogue = remember {
        mutableStateOf(true)
    }
    if (openDialogue.value) {
        AlertDialog(
            onDismissRequest = { openDialogue.value = false },
            title = {
                Text(text = "Location")
            },
            text = {
                Text(text = "Enable location")
            },
            confirmButton = {
                Button(onClick = {
                    openDialogue.value = false
                    context.openSettings()
                }) {
                    Text(text = "Open Setting")
                }
            },
            backgroundColor = LightBg,
            contentColor = MainBg,
        )
    }
}