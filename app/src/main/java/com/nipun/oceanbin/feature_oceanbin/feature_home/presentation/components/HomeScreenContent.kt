package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.openSettings
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    navController: NavController,
    bottomNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    if (!homeViewModel.hasPermission && homeViewModel.count == Constant.Max_Count) {
        homeViewModel.setCount()
        EnableLocationBySetting()
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(
            BottomSheetValue.Expanded
        )
    )
    var size by remember {
        mutableStateOf(0.78f)
    }
    val sizeAnimate by animateFloatAsState(
        targetValue = size,
        animationSpec = tween(
            durationMillis = 250
        )
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BoxContents(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(sizeAnimate),
                bottomNavController = bottomNavController,
                homeViewModel = homeViewModel,
                navController = navController,
                expanded = bottomSheetScaffoldState.bottomSheetState.isExpanded,
                onDrag = {
                    if (it > 0)
                        size = 0.87f
                }
            ) {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.animateTo(
                            targetValue = BottomSheetValue.Collapsed
                        )
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.animateTo(
                            targetValue = BottomSheetValue.Expanded
                        )
                    }
                }
            }
        },
        sheetPeekHeight = CurveHeight,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = DrawerHeight)
    ) {
        TopWeather(
            modifier = Modifier
                .background(LightBg)
                .padding(
                    start = MediumSpacing,
                    end = MediumSpacing,
                    top = MediumSpacing
                )
                .fillMaxSize(),
            homeViewModel = homeViewModel
        )
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