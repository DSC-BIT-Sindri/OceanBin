package com.nipun.oceanbin.feature_oceanbin.presentation

import com.nipun.oceanbin.R

sealed class BottomScreens(val name : String,val route : String,val id : Int){
    object HomeScreen : BottomScreens("Home","home", R.drawable.ic_next)
    object ProfileScreen : BottomScreens("Profile","profile",R.drawable.ic_next)
}

val bottomItems = listOf(
    BottomScreens.HomeScreen,
    BottomScreens.ProfileScreen
)
