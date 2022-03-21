package com.nipun.oceanbin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nipun.oceanbin.feature_oceanbin.BottomScreen
import com.nipun.oceanbin.firsttime_display.*
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.Signup
import com.nipun.oceanbin.firsttime_display.feature_login.presentation.Login
import com.nipun.oceanbin.ui.theme.SplashScreenTutTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SplashScreenTutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /* This nav controller is main screen navigator,
                     * We can use this to navigate like another activity in xml project
                     */
                    val navController = rememberNavController()
                    // Main Screen Nav Host
                    NavHost(
                        navController = navController,
                        // If app is just installed our splash screen will be shown else our main content will gonna shown.
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            // Splash screen
                            SplashScreen(navController = navController, mainViewModel)
                        }
                        composable(route = Screen.WhoAreYouScreen.route) {
                            // Who are you screen
                            WhoAreYouScreen(navController = navController)
                        }
                        composable(route = Screen.SplashSeaViewPager.route) {
                            SplashViewPager(
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
                        composable(route = Screen.SplashBeachViewPager.route) {
                            SplashBeachViewPager(
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
                        composable(route = Screen.DoLoginSignup.route) {
                            DoLoginSignup(
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
                        composable(route = Screen.Login.route) {
                            Login(navController = navController)
                        }
                        composable(route = Screen.Signup.route) {
                            Signup(navController = navController)
                        }
                        composable(route = Screen.BottomScreen.route) {
                            BottomScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}