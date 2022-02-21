package com.nipun.oceanbin.feature_oceanbin.presentation.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController: NavController
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val height = constraints.maxHeight
        val minOffSet = height * 0.15f
        val maxOffset = height * 0.67f
        var offsetY by remember { mutableStateOf(minOffSet) }
        Column(
            modifier = Modifier
                .background(LightBg)
                .padding(MediumSpacing)
                .fillMaxWidth()
                .fillMaxHeight(0.77f),
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
                        offsetY += y
                    }
                }
                .offset {
                    IntOffset(
                        0,
                        minOf(
                            maxOffset.roundToInt(),
                            maxOf(minOffSet.roundToInt(), offsetY.roundToInt())
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
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(100) {
                    Text(
                        text = "${it + 1}",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}