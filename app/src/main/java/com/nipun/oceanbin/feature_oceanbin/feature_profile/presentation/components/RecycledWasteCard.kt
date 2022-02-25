package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.ui.theme.MediumSpacing
import com.nipun.oceanbin.ui.theme.RobotoFamily
import com.nipun.oceanbin.ui.theme.SmallSpacing

@Composable
fun RecycledWasteCard(
    modifier: Modifier = Modifier.fillMaxWidth(),
    weight : Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "My Recycled Waste",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        Card(
            modifier = modifier,
            elevation = SmallSpacing,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier.padding(15.dp, 10.dp)
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
            }
        }
    }
}

// function to display the linear bar of Weight Recycled
//@Composable
//fun LinearProgressBar(
//    value : Int,
//    maxValue: Int,
//    fontSize : TextUnit = 15.sp,
//    color: Color = Color.Green,
//    strokeWidth : Dp = 5.dp,
//    animationDuration : Int = 1000,
//    animationDelay : Int = 0,
//    modifier: Modifier = Modifier
//) {
//    var percentage : Float = value.toFloat()/maxValue.toFloat() + 100
//
//    var animationPlayed by remember {
//        mutableStateOf(false)
//    }
//
//    var curPercentage = animateFloatAsState(
//        targetValue = if(animationPlayed) percentage else 0f,
//        animationSpec = tween(
//            durationMillis = animationDuration,
//            delayMillis = animationDelay
//        )
//    )
//
//    LaunchedEffect(key1 = true){
//        animationPlayed = true
//    }
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//    ) {
//        Canvas(modifier = modifier.size(50.dp)){
//            drawLine(
//                color = color,
//                0f,
//                value.toFloat(),
//                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
//            )
//        }
//    }
//
//}