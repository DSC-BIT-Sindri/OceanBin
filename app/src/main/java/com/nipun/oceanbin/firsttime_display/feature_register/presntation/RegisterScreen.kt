package com.nipun.oceanbin.firsttime_display.feature_register.presntation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.core.showToast
import com.nipun.oceanbin.firsttime_display.CustomButton
import com.nipun.oceanbin.firsttime_display.Field
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.ui.Screen
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Signup(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val showLoading = registerViewModel.showLoading.value
    val context = LocalContext.current

    LaunchedEffect(
        key1 = true,
        block = {
            registerViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.name
                        )
                    }
                    is UIEvent.GoNext -> {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = event.name
//                        )
                        context.showToast(event.name)
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.DoLoginSignup.route)
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
                .background(LightBlue),
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
                    .height(RegisterImageSize)
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
                        .padding(SmallSpacing)
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "Signup",
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.size(BigSpacing))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .verticalScroll(state = rememberScrollState(), enabled = true)
            ) {
                Spacer(modifier = Modifier.height(RegisterImageSize + BigSpacing))
                Field(
                    identity = "Name",
                    textState = registerViewModel.name.value
                ) {
                    registerViewModel.changeValue(
                        TextChangeEvent.Name(it)
                    )
                }
                Field(
                    identity = "Mobile",
                    textState = registerViewModel.phone.value,
                    isNumber = true
                ) {
                    registerViewModel.changeValue(
                        TextChangeEvent.Phone(it)
                    )
                }
                Field(
                    identity = "Email",
                    textState = registerViewModel.email.value,
                    isEmail = true
                ) {
                    registerViewModel.changeValue(
                        TextChangeEvent.Email(it)
                    )
                }
                Field(
                    identity = "Password",
                    textState = registerViewModel.password.value,
                    isPassword = true
                ) {
                    registerViewModel.changeValue(
                        TextChangeEvent.Password(it)
                    )
                }
                Field(
                    identity = "Confirm Password",
                    textState = registerViewModel.confirmPassword.value,
                    isPassword = true,
                    isDone = true,
                    onDoneClick = {
                        registerViewModel.createUser()
                    }
                ) {
                    registerViewModel.changeValue(
                        TextChangeEvent.ConfirmPassword(it)
                    )
                }
                Spacer(modifier = Modifier.padding(top = MediumSpacing))
                CustomButton(text = "Sign Up",
                    onButtonClick = {
                        registerViewModel.createUser()
                    }
                )
                Spacer(modifier = Modifier.padding(top = BigSpacing))
                Text(
                    text = "By signing up, you agree to our",
                    fontSize = SmallTextSize,
                    color = TextLoginColor,
                    modifier = Modifier.clickable(onClick = {})
                )
                Text(
                    text = "Terms & Cookies Policy.",
                    style = Typography.h3,
                    color = TextLoginColor,
                    modifier = Modifier.clickable(onClick = {})
                )
                Spacer(modifier = Modifier.size(MediumSpacing))
            }
        }
    }
}