package com.nipun.oceanbin.firsttime_display.feature_login.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextState
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

    BackHandler(enabled = signInViewModel.resetPasswordDialogue.value) {
        signInViewModel.changePassWordVisibility(false)
    }

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
            if (signInViewModel.resetPasswordDialogue.value) {
                PasswordResetDialogue(
                    textState = signInViewModel.email.value,
                    showLoading = showLoading,
                    onFieldValueChange = {
                        signInViewModel.changeValue(
                            TextChangeEvent.Email(it)
                        )
                    },
                    onYesClick = {
                        signInViewModel.resetPassword()
                    }
                ) {
                    signInViewModel.changeValue(
                        TextChangeEvent.Email("")
                    )
                    signInViewModel.changePassWordVisibility(false)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.SpaceBetween,
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
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_leaf),
                    contentDescription = "Signup",
                    contentScale = ContentScale.Inside,
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
                    textState = signInViewModel.email.value,
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
                    modifier = Modifier.clickable(onClick = {
                        signInViewModel.changePassWordVisibility(true)
                    })
                )
                Spacer(modifier = Modifier.size(BigSpacing))
            }
        }
    }
}

@Composable
fun PasswordResetDialogue(
    textState: TextState,
    showLoading: Boolean,
    onFieldValueChange: (String) -> Unit,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SmallSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.reset_password),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(MediumSpacing))
                    OutlinedTextField(
                        value = textState.text,
                        onValueChange = {
                            onFieldValueChange(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.body1,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.White,
                            focusedIndicatorColor = WhiteShade,
                            unfocusedIndicatorColor = WhiteShade,
                            cursorColor = WhiteShade
                        ),
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.overline,
                                color = WhiteShade
                            )
                        }
                    )
                }
                AnimatedVisibility(visible = showLoading) {
                    CircularProgressIndicator(
                        color = GreenBorder
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onYesClick()
                    }
                ) {
                    Text("Send", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    // adding on click listener for this button
                    onClick = {
                        onCancelClick()
                    }
                ) {
                    // adding text to our button.
                    Text("Cancel", color = Color.Red)
                }
            },
            backgroundColor = LightBg,
            contentColor = MainBg
        )
    }
}