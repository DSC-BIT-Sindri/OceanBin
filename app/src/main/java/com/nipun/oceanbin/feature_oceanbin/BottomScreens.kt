package com.nipun.oceanbin.feature_oceanbin

import com.nipun.oceanbin.R

sealed class BottomScreens(val name : String,val route : String,val id : Int){
    object HomeScreen : BottomScreens("Home","home", R.drawable.ic_home)
    object NewsScreen : BottomScreens("News","news",R.drawable.ic_news)
    object MapScreen : BottomScreens("Map","map",R.drawable.ic_truck)
    object WeatherScreen : BottomScreens("Weather","weather",R.drawable.ic_cloud_without_sun)
    object ProfileScreen : BottomScreens("Profile","profile",R.drawable.ic_profile)
    object SearchScreen : BottomScreens("Search","search",0)
}

val bottomItems = listOf(
    BottomScreens.HomeScreen,
    BottomScreens.NewsScreen,
    BottomScreens.MapScreen,
    BottomScreens.WeatherScreen,
    BottomScreens.ProfileScreen
)
