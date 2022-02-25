package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.*

@Composable
fun BoxContents(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
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
                color = ShadowColor,
                style = Stroke(width = 8f)
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
                    .fillMaxHeight(),
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
        }
    }
}