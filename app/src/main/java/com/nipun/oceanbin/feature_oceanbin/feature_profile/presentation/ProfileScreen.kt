package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.nipun.oceanbin.ui.theme.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.*
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.PersonalDetails
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.RecycledWasteCard
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.WalletDetailsCard
import com.nipun.oceanbin.ui.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ProfileScreenDetails(modifier, navController)
}

@Composable
fun ProfileScreenDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val user = profileViewModel.user.value
    val scaffoldState = rememberScaffoldState()
    val showPermission = profileViewModel.showPermissionDialogue.value
    val previousDeny = profileViewModel.previousDenied.value
    val context = LocalContext.current
    val profileImage = user.image
    val showLoading = profileViewModel.showLoading.value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            profileViewModel.getBitMap(it)
        }
    )
    LaunchedEffect(
        key1 = true,
        block = {
            profileViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.name
                        )
                    }
                    else -> {}
                }
            }
        }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = TopbarLightBlue,
        modifier = modifier,
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        if (showPermission) {
            PermissionUi(
                context = context,
                permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                permanentDenyMessage = stringResource(id = R.string.read_storage_permanent_deny),
                permissionRational = stringResource(id = R.string.read_storage_rational),
                scaffoldState = scaffoldState,
                permanentDeny = previousDeny,
                permissionAction = { action ->
                    when (action) {
                        is PermissionAction.OnPermissionDenied -> {

                            profileViewModel.changeLocationPermissionDialogueValue(false)
                        }
                        is PermissionAction.OnPermissionGranted -> {
                            launcher.launch("image/*")
                            profileViewModel.changeLocationPermissionDialogueValue(false)
                        }
                        is PermissionAction.OnPreviouslyDenied -> {
                            profileViewModel.setPreviouslyDenied()
                        }
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DefaultSnackbar(snackbarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier
                    .padding(bottom = DrawerHeight)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = SmallSpacing)
                    .zIndex(101f),
                onAction = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
                }
            )
            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(101f)
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LogoWithText(
                    modifier,
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
                Box(
                    modifier = Modifier
                        .size(ProfileImageSize)
                ) {
                    Image(
                        modifier = modifier
                            .size(ProfileImageSize)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .border(3.dp, MainBg, CircleShape),
                        contentScale = ContentScale.Crop,
                        painter = if (profileImage.isBlank() || profileImage == "null") painterResource(
                            id = R.drawable.ic_profile
                        ) else rememberImagePainter(profileImage) {
                            placeholder(R.drawable.ic_profile)
                        },
                        contentDescription = "Profile Picture"
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit profile",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(
                                bottom = ExtraSmallSpacing,
                                end = ExtraSmallSpacing
                            )
                            .clickable {
                                profileViewModel.changeLocationPermissionDialogueValue(true)
                            }
                            .size(IconSize)
                            .clip(CircleShape)
                            .border(
                                width = BigStroke,
                                shape = CircleShape,
                                color = Color.Gray
                            )
                            .background(MainBg)
                            .padding(SmallSpacing)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.755f)
                    .align(Alignment.BottomCenter)
                    .zIndex(-1f),
                shape = RoundedCornerShape(BigSpacing)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = BigSpacing)
                ) {
                    Spacer(modifier = Modifier.height(DrawerHeight))
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(vertical = BigSpacing)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(SmallSpacing))
                            RecycledWasteCard(
                                weight = 75,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.size(MediumSpacing))
                            WalletDetailsCard(
                                balance = 1000,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.size(MediumSpacing))
                            PersonalDetails(
                                user = user,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = SmallSpacing
                                    )
                            )
                        }

                        item {
                            Button(onClick = {
                                profileViewModel.logout()
                                navController.navigate(Screen.DoLoginSignup.route) {
                                    popUpTo(Screen.BottomScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }) {
                                Text(text = "Logout")
                            }
                            Spacer(modifier = Modifier.size(SmallSpacing))
                        }
                    }
                }
            }
        }
    }
}





