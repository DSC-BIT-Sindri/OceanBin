package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.ui.theme.*

@Composable
fun RecycledWasteCard(
    modifier: Modifier = Modifier,
    weight: Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "My Recycled Waste",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(start = BigSpacing)
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        Row(
            modifier
                .padding(
                    IconSize, SmallSpacing
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "$weight Kg",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "This Month",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            LinearProgressBar(
                value = 75,
                maxValue = 100,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}

// function to display the linear bar of Weight Recycled
@Composable
fun LinearProgressBar(
    value: Int,
    maxValue: Int,
    fontSize: TextUnit = 15.sp,
    color: Color = Color.Green,
    stroke_Width: Float = 15f,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    modifier: Modifier = Modifier
) {
    var percentage: Float = value.toFloat() / maxValue.toFloat()

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(18.dp, 10.dp)
    ) {
        Column(
            modifier = modifier,
//                .padding(23.dp, 10.dp, 23.dp, 0.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = modifier
            ) {
                drawLine(
                    color = TopbarLightBlue,
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    strokeWidth = stroke_Width
                )
                drawLine(
                    color = color,
                    Offset(0f, 0f),
                    Offset(curPercentage.value * size.width, 0f),
                    strokeWidth = stroke_Width
                )
            }

            Spacer(modifier = Modifier.size(ExtraSmallSpacing))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "0 Kg",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$maxValue Kg",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

        }
    }

}