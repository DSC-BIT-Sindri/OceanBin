package com.nipun.oceanbin.feature_oceanbin

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nipun.oceanbin.core.Constant.LAT
import com.nipun.oceanbin.core.Constant.LON
import com.nipun.oceanbin.core.Constant.NEWS
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeScreen
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.MapScreen
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.NewsScreen
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.ProfileScreen
import com.nipun.oceanbin.feature_oceanbin.feature_search.presentation.SearchScreen
import com.nipun.oceanbin.feature_oceanbin.feature_weather.presentation.WeatherScreen
import com.nipun.oceanbin.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bottomNavController = rememberAnimatedNavController()
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
        AnimatedNavHost(
            navController = bottomNavController,
            startDestination = BottomScreens.HomeScreen.route
        ) {
            composable(BottomScreens.HomeScreen.route) {
                HomeScreen(navController = navController, bottomNavController = bottomNavController)
            }
            composable(
                route = BottomScreens.NewsScreen.route + "?$NEWS={$NEWS}",
                arguments = listOf(
                    navArgument(NEWS) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                NewsScreen(navController = navController)
            }
            composable(
                route = BottomScreens.MapScreen.route + "?$LAT={$LAT}&$LON={$LON}",
                arguments = listOf(
                    navArgument(LAT) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument(LON) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                MapScreen(
                    navController = navController,
                    bottomNavController = bottomNavController
                )
            }
            composable(BottomScreens.WeatherScreen.route) {
                WeatherScreen(navController = navController)
            }
            composable(BottomScreens.ProfileScreen.route) {
                ProfileScreen(navController = navController)
            }
            composable(
                route = BottomScreens.SearchScreen.route + "?$LAT={$LAT}&$LON={$LON}",
                arguments = listOf(
                    navArgument(LAT) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    },
                    navArgument(LON) {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                ),
                enterTransition = {
                    enterAnimation()
                },
                exitTransition = {
                    exitAnimation()
                },
                popExitTransition = {
                    exitAnimation()
                }
            ) {
                SearchScreen(bottomNavController = bottomNavController)
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
            val selected =
                currentRoute?.let { route -> route.takeWhile { it != '?' } == screen.route } == true
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