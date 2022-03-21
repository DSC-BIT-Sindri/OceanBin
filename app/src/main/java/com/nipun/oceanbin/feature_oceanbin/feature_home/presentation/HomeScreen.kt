package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import android.Manifest
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.*
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.BoxContents
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.TopWeather
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    bottomNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var size by remember {
        mutableStateOf(0.78f)
    }
    val permissionShowDialogue = homeViewModel.showPermissionDialogue.value
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(
            if (permissionShowDialogue) BottomSheetValue.Collapsed else BottomSheetValue.Expanded
        )
    )
    val sizeAnimate by animateFloatAsState(
        targetValue = size,
        animationSpec = tween(
            durationMillis = 250
        )
    )
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        snackbarHost = { bottomSheetScaffoldState.snackbarHostState },
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
        if (permissionShowDialogue) {
            MultiplePermissionUi(
                context = context,
                permission = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionRational = stringResource(id = R.string.show_location_quote),
                permanentDenyMessage = stringResource(id = R.string.setting_location_dialogue),
                scaffoldState = bottomSheetScaffoldState,
                permanentDeny = homeViewModel.count == Constant.Max_Count,
                permissionAction = { action ->
                    when (action) {
                        is PermissionAction.OnPermissionGranted -> {
                            homeViewModel.getLocation()
                        }
                        is PermissionAction.OnPermissionDenied -> {
                            homeViewModel.setCount()
                        }else -> {}
                    }
                    homeViewModel.changeLocationPermissionDialogueValue(false)
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
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
            DefaultSnackbar(snackbarHostState = bottomSheetScaffoldState.snackbarHostState,
                modifier = Modifier
                    .padding(bottom = DrawerHeight)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = SmallSpacing)
                    .zIndex(101f),
                onAction = {
                    bottomSheetScaffoldState.snackbarHostState.currentSnackbarData?.performAction()
                }
            )
        }
    }
}