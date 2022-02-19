package com.nipun.oceanbin.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nipun.oceanbin.core.standardQuadFromTo
import com.nipun.oceanbin.ui.theme.LightBg
import com.nipun.oceanbin.ui.theme.LightBgShade
import com.nipun.oceanbin.ui.theme.MediumSpacing
import com.nipun.oceanbin.ui.theme.Screen
import kotlinx.coroutines.launch

@Composable
fun Test() {
    Text(text = "Ok")
}

@Composable
fun SplashViewPager(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val point1 = Offset(0f, height * 0.3f)
        val point2 = Offset(0.1f * width, height * 0.14f)
        val point3 = Offset(0.55f * width, height * 0.19f)
        val point4 = Offset(1.1f * width, height * 0.02f)
        val point5 = Offset(1.15f * width, height * 0.5f)
        val point5a = Offset(1.25f * width, height * 0.7f)
        val point6 = Offset(0.9f * width, height * 0.8f)
        val point7 = Offset(0.7f * width, height * 0.97f)
        val point8 = Offset(0.25f * width, height * 0.9f)
        val point9 = Offset(0.1f, height * 1f)
        val point10 = Offset(0f, height * 1f)

        val lightColorPath = Path().apply {
            moveTo(point1.x, point1.y)
            standardQuadFromTo(point1, point2)
            standardQuadFromTo(point2, point3)
            standardQuadFromTo(point3, point4)
            standardQuadFromTo(point4, point5)
            lineTo(point5a.x,point5a.y)
            standardQuadFromTo(point5a, point6)
            standardQuadFromTo(point6, point7)
            standardQuadFromTo(point7, point8)
            standardQuadFromTo(point8, point9)
            lineTo(point10.x, point10.y)
            close()
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPath(lightColorPath, color = LightBgShade)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SetupViewPager(
                modifier = Modifier.fillMaxSize()
            ){
                mainViewModel.setSplashViewed()
                navController.navigate(Screen.HomeScreen.route){
                    popUpTo(Screen.SplashScreen.route){
                        inclusive = true
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupViewPager(
    modifier: Modifier = Modifier,
    onContinueClick : () -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = 4,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${index + 1} page",
                    style = MaterialTheme.typography.h3
                )
                if(pagerState.currentPage == 3){
                    Spacer(modifier = Modifier.size(MediumSpacing))
                    Button(onClick = { onContinueClick() }) {
                        Text(text = "Continue",style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
        if (pagerState.currentPage != 3) {
            Button(
                onClick =
                {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = 3
                        )
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(text = "SKIP")
            }
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            if (pagerState.currentPage != 0) {
                Button(
                    onClick =
                    {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage - 1
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(text = "PREV")
                }
            }
            if (pagerState.currentPage != 3) {
                Button(
                    onClick =
                    {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(text = "NEXT")
                }
            }
            Dots(
                dotCount = 4, currentSelect = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Dots(
    modifier: Modifier = Modifier,
    dotCount: Int,
    currentSelect: Int
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = scrollState) {
        scrollState.scrollTo(currentSelect)
    }
    Row(
        modifier = modifier
            .horizontalScroll(scrollState, true),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until dotCount) {
            Surface(
                modifier = Modifier
                    .size(12.dp),
                shape = CircleShape,
                color = if (i == currentSelect) Color.Gray else Color.White,
                elevation = 1.dp
            ) {}
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}
