package com.nipun.oceanbin.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

@OptIn(ExperimentalAnimationApi::class)
fun enterAnimation() : EnterTransition = scaleIn(
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

@OptIn(ExperimentalAnimationApi::class)
fun exitAnimation() : ExitTransition = scaleOut(
    animationSpec = tween(
        durationMillis = 300,
        easing = LinearOutSlowInEasing
    )
)