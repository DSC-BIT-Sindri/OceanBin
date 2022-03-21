package com.nipun.oceanbin.firsttime_display.feature_login.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.firsttime_display.CustomButton
import com.nipun.oceanbin.firsttime_display.Field
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.ui.Screen
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Login(
    navController: NavController,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val showLoading = signInViewModel.showLoading.value

    LaunchedEffect(
        key1 = true,
        block = {
            signInViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.name
                        )
                    }
                    is UIEvent.GoNext -> {
                        navController.navigate(Screen.BottomScreen.route) {
                            popUpTo(Screen.DoLoginSignup.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlue)
        ) {
            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(10f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoWithText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = ExtraBigSpacing),
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(SmallSpacing),
                    painter = painterResource(id = R.drawable.ic_leaf),
                    contentDescription = "Signup",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .align(Alignment.BottomCenter)
            ) {
                Field(
                    identity = "Email",
                    signInViewModel.email.value,
                    isEmail = true
                ) {
                    signInViewModel.changeValue(
                        TextChangeEvent.Email(it)
                    )
                }
                Field(
                    identity = "Password",
                    textState = signInViewModel.password.value,
                    isPassword = true,
                    isDone = true
                ) {
                    signInViewModel.changeValue(
                        TextChangeEvent.Password(it)
                    )
                }
                Spacer(modifier = Modifier.padding(top = MediumSpacing))
                CustomButton(text = "Sign In",
                    onButtonClick = {
                        signInViewModel.signInUser()
                    }
                )
                Spacer(modifier = Modifier.padding(BigSpacing))
                Text(
                    text = "Forgot Password?", style = Typography.h3,
                    modifier = Modifier.clickable(onClick = {})
                )
                Spacer(modifier = Modifier.size(BigSpacing))
            }
        }
    }
}