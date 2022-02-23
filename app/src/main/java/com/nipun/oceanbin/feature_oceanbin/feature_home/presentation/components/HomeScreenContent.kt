package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.openSettings
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.LightBg
import com.nipun.oceanbin.ui.theme.MainBg
import com.nipun.oceanbin.ui.theme.MediumSpacing
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreenContent(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    if (!homeViewModel.hasPermission && homeViewModel.count == Constant.Max_Count) {
        homeViewModel.setCount()
        EnableLocationBySetting()
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = constraints.maxHeight
        val minOffSet = height * 0.16f
        val maxOffset = height * 0.77f
        val scrollOffset = 20f
        val offSetYAnimate = remember {
            Animatable(minOffSet)
        }
        val coroutineScope = rememberCoroutineScope()
        TopWeather(
            modifier = Modifier
                .background(LightBg)
                .padding(MediumSpacing)
                .fillMaxWidth()
                .fillMaxHeight(0.87f),
            homeViewModel = homeViewModel
        )
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, _, _ ->
                        val y = pan.y
                        coroutineScope.launch {
                            if (y >= scrollOffset) {
                                offSetYAnimate.animateTo(
                                    targetValue = maxOffset,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        delayMillis = 0
                                    )
                                )
                            } else if (y <= -scrollOffset) {
                                offSetYAnimate.animateTo(
                                    targetValue = minOffSet,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        delayMillis = 0
                                    )
                                )
                            }
                        }
                    }
                }
                .offset {
                    IntOffset(
                        0,
                        minOf(
                            maxOffset.roundToInt(),
                            maxOf(minOffSet.roundToInt(), offSetYAnimate.value.roundToInt())
                        )
                    )
                }
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            BoxContents(
                modifier = Modifier.fillMaxSize(),
                homeViewModel = homeViewModel,
                navController = navController
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
    if(openDialogue.value) {
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