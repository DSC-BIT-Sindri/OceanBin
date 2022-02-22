package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.BoxContents
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.TopWeather
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
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