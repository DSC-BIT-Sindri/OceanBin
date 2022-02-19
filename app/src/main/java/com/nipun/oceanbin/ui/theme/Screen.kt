package com.nipun.oceanbin.ui.theme

sealed class Screen(val route : String,val label : String = ""){
    object SplashScreen : Screen(route = "splash")
    object HomeScreen : Screen(route = "home")
}
