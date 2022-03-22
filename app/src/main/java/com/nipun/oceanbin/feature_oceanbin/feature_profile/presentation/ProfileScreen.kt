package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.nipun.oceanbin.firsttime_display.feature_login.presentation.PasswordResetDialogue
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextState
import com.nipun.oceanbin.ui.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ProfileScreenDetails(modifier, navController)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreenDetails(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(
            initialValue = BottomSheetValue.Expanded
        )
    )
    val showPermission = profileViewModel.showPermissionDialogue.value
    val previousDeny = profileViewModel.previousDenied.value
    val context = LocalContext.current
    val profileImage = profileViewModel.user.value.image

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    profileViewModel.getBitMap(uri = uri)
                }
            }
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
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            ProfileBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.755f)
                    .padding(bottom = DrawerHeight)
                    .zIndex(-1f),
                navController = navController,
                profileViewModel = profileViewModel,
                scaffoldState = scaffoldState
            )
        },
        sheetPeekHeight = 350.dp,
        sheetBackgroundColor = Color.Transparent,
        floatingActionButton = {
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
        },
        floatingActionButtonPosition = FabPosition.Center,
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
                            Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            ).apply {
                                type = "image/*"
                                launcher.launch(this)

                            }
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
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LogoWithText(
                    modifier,
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
                Spacer(modifier = Modifier.size(MediumSpacing))
                Image(
                    painter = painterResource(id = R.drawable.ic_android_download),
                    contentDescription = "Qr",
                    modifier = Modifier
                        .padding(SmallSpacing)
                        .size(QrSize)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileBottomSheet(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    scaffoldState: BottomSheetScaffoldState
) {
    val user = profileViewModel.user.value
    val showLoading = profileViewModel.showLoading.value
    val nameState = profileViewModel.name.value
    val phoneState = profileViewModel.phone.value

    var nameEditVisible by remember {
        mutableStateOf(false)
    }
    var phoneEditVisible by remember {
        mutableStateOf(false)
    }

    var changePasswordVisibility by remember {
        mutableStateOf(false)
    }

    var logoutVisibility by remember {
        mutableStateOf(false)
    }

    val showSaveChangeValue = (nameState.text != user.name) || (phoneState.text != user.phone)

    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = WhiteShade,
            shape = RoundedCornerShape(
                topStart = MediumSpacing,
                topEnd = MediumSpacing
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = SmallSpacing)
            ) {
                Spacer(modifier = Modifier.height(DrawerHeight))
                Column(
                    Modifier
                        .verticalScroll(
                            state = rememberScrollState(),
                            enabled = true
                        )
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    Spacer(modifier = Modifier.size(MediumSpacing))
                    PersonalDetails(
                        user = user,
                        nameState = nameState,
                        phoneState = phoneState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = SmallSpacing
                            ),
                        onNameEditClick = {
                            nameEditVisible = true
                        },
                        onPhoneEditClick = {
                            phoneEditVisible = true
                        },
                        onChangePasswordClick = {
                            changePasswordVisibility = true
                        }
                    )
                    if (!showSaveChangeValue) {
                        CustomButton(
                            title = R.string.logout,
                            icon = R.drawable.ic_back,
                            modifier = Modifier
                                .padding(SmallSpacing)
                        ) {
                            logoutVisibility = true
                        }
                    }
                }
            }
        }
        if (showSaveChangeValue) {
            CustomButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(SmallSpacing),
                title = R.string.save_changes
            ) {
                profileViewModel.changeUserDetail()
            }
        }

        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(101f)
            )
        }
        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            modifier = Modifier
                .padding(bottom = DrawerHeight)
                .align(Alignment.BottomCenter)
                .padding(bottom = SmallSpacing)
                .zIndex(101f),
            onAction = {
                scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
            }
        )
        if (changePasswordVisibility) {
            CustomAlertDialogue(title = R.string.change_password,
                text = R.string.change_password_text,
                onYesClick = {
                    profileViewModel.changePassword()
                    changePasswordVisibility = false
                }) {
                changePasswordVisibility = false
            }
        }
        if (logoutVisibility) {
            CustomAlertDialogue(title = R.string.logout,
                text = R.string.logout_dialogue,
                onYesClick = {
                    logoutVisibility = false
                    profileViewModel.logout()
                    navController.navigate(Screen.DoLoginSignup.route) {
                        popUpTo(Screen.BottomScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                logoutVisibility = false
            }
        }
        if (nameEditVisible) {
            FieldEditDialogue(
                header = "Name",
                title = R.string.change_name,
                textState = nameState,
                showLoading = showLoading,
                onFieldValueChange = {
                    profileViewModel.changeFieldValue(
                        TextChangeEvent.Name(it)
                    )
                },
                onYesClick = {
                    nameEditVisible = false
                }) {
                profileViewModel.changeFieldValue(
                    TextChangeEvent.Name(user.name)
                )
                nameEditVisible = false
            }
        }
        if (phoneEditVisible) {
            FieldEditDialogue(
                header = "Phone",
                title = R.string.change_phone,
                textState = phoneState,
                isPhone = true,
                showLoading = showLoading,
                onFieldValueChange = {
                    profileViewModel.changeFieldValue(
                        TextChangeEvent.Phone(it)
                    )
                },
                onYesClick = {
                    phoneEditVisible = false
                }) {
                profileViewModel.changeFieldValue(
                    TextChangeEvent.Phone(user.phone)
                )
                phoneEditVisible = false
            }
        }
    }
}

@Composable
fun FieldEditDialogue(
    header: String = "Email",
    title: Int,
    isPhone: Boolean = false,
    textState: TextState,
    showLoading: Boolean,
    onFieldValueChange: (String) -> Unit,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SmallSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = title),
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
                            .fillMaxWidth()
                            .focusRequester(focusRequester = focusRequester),
                        textStyle = MaterialTheme.typography.body1,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.White,
                            focusedIndicatorColor = WhiteShade,
                            unfocusedIndicatorColor = WhiteShade,
                            cursorColor = WhiteShade
                        ),
                        keyboardOptions = when {
                            isPhone -> KeyboardOptions(keyboardType = KeyboardType.Number)
                            else -> KeyboardOptions(keyboardType = KeyboardType.Text)
                        },
                        label = {
                            Text(
                                text = header,
                                style = MaterialTheme.typography.overline,
                                color = WhiteShade
                            )
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onYesClick()
                    }
                ) {
                    Text("Ok", color = Color.White)
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
        if (showLoading) {
            CircularProgressIndicator(
                color = GreenBorder,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
    LaunchedEffect(
        key1 = true,
        block = {
            focusRequester.requestFocus()
        }
    )
}





