package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController: NavController
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = constraints.maxHeight
        val minOffSet = height * 0.1f
        val maxOffset = height * 0.77f
        val scrollOffset = 20f
        val offSetYAnimate = remember {
            Animatable(minOffSet)
        }
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .background(LightBg)
                .padding(MediumSpacing)
                .fillMaxWidth()
                .fillMaxHeight(0.87f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = "OCEANBIN",
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
        }
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BoxContents(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        ) {
            drawPath(
                getMainScreenCurve(width, height),
                color = Color.Black,
                style = Stroke(width = 12f)
            )
            drawPath(
                getMainScreenCurve(width, height),
                color = MainBg,
            )
        }
        Column(
            modifier = modifier
                .padding(top = CurveHeight)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(SmallSpacing),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.89f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(100) {
                    Text(
                        text = "${it + 1}",
                        style = MaterialTheme.typography.body1
                    )
                }
                item { 
                    Spacer(modifier = Modifier.size(DrawerHeight))
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight())
        }
    }
}