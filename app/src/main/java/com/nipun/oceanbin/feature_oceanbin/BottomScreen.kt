package com.nipun.oceanbin.feature_oceanbin

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.MapScreen
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.NewsScreen
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.WeatherScreen
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeScreen
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.ProfileScreen
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BottomScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bottomNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(SmallSpacing)
                        .fillMaxWidth(),
                    elevation = ExtraSmallSpacing,
                    backgroundColor = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(SmallSpacing),
                    contentColor = Color.Black,
                    snackbarData = data
                )
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route
            BottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DrawerHeight),
                currentRoute = currentDestination
            ) { dest ->
                bottomNavController.navigate(dest) {
                    popUpTo(BottomScreens.HomeScreen.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        }
    ) {
        NavHost(
            navController = bottomNavController,
            startDestination = BottomScreens.HomeScreen.route
        ) {
            composable(BottomScreens.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomScreens.NewsScreen.route){
                NewsScreen(navController = navController)
            }
            composable(BottomScreens.MapScreen.route){
                MapScreen(navController = navController)
            }
            composable(BottomScreens.WeatherScreen.route){
                WeatherScreen(navController = navController)
            }
            composable(BottomScreens.ProfileScreen.route) {
                ProfileScreen(navController = navController)
            }

        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onClick: (String) -> Unit
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MainBg,
        elevation = SmallSpacing
    ) {
        bottomItems.forEach { screen ->
        val selected = currentRoute?.let { it == screen.route } == true
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.id),
                        contentDescription = screen.name,
                        modifier = Modifier
                            .size(if (selected) 45.dp else IconSize)
                            .padding(ExtraSmallSpacing),
                    )
                },
                selected = selected,
                onClick = { onClick(screen.route) },
                selectedContentColor = LightBg,
                unselectedContentColor = Color.Gray
            )
        }
    }
}