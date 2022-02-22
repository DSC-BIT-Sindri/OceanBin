package com.nipun.oceanbin.feature_oceanbin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nipun.oceanbin.feature_oceanbin.presentation.screens.HomeScreen
import com.nipun.oceanbin.feature_oceanbin.presentation.screens.ProfileScreen
import com.nipun.oceanbin.ui.theme.*

@Composable
fun BottomScreen(
    navController: NavController
) {
    val bottomNavController = rememberNavController()
    Scaffold(
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
                    restoreState = true
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
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.id),
                        contentDescription = screen.name,
                        modifier = Modifier
                            .size(IconSize)
                            .padding(ExtraSmallSpacing),
                    )
                },
                selected = currentRoute?.let { it == screen.route } == true,
                onClick = { onClick(screen.route) },
                selectedContentColor = LightBg,
                unselectedContentColor = Color.Gray
            )
        }
    }
}