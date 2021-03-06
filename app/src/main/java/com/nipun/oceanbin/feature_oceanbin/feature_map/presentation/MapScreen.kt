package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.*
import com.nipun.oceanbin.core.Constant.LAT
import com.nipun.oceanbin.core.Constant.LON
import com.nipun.oceanbin.feature_oceanbin.BottomScreens
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.DropDownData
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.components.PickupDropDown
import com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.components.timeAvailable
import com.nipun.oceanbin.ui.Screen
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    navController: NavController,
    bottomNavController: NavController,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val locationState = mapViewModel.location.value
    val destinationLatLng = locationState.data.latLang
    val currentLocation = mapViewModel.currentLocation.value
    val address = locationState.data.address.let { it.ifBlank { currentLocation.address } }
    val addressLine =
        locationState.data.addressLine.let { it.ifBlank { currentLocation.addressLine } }

    val markerLocation = destinationLatLng ?: currentLocation.latLang ?: LatLng(0.0, 0.0)

    var zoomState by remember {
        mutableStateOf(11f)
    }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val size by animateFloatAsState(
        targetValue = zoomState,
        tween(
            durationMillis = 500,
            delayMillis = 0
        )
    )
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation.latLang ?: LatLng(0.0, 0.0),
            size
        )
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true,
            maxZoomPreference = 20f,
            minZoomPreference = 2f
        )
    }
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false
        )
    }

    val showLoading = mapViewModel.showLoading.value

    LaunchedEffect(
        key1 = true,
        block = {
            mapViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        context.showToast(event.message)
                    }
                }
            }
        }
    )
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomDialogueForMap(
                modifier = Modifier
                    .fillMaxWidth(),
                address = addressLine,
                dateSelectedIndex = mapViewModel.dateIndex.value,
                timeSelectedIndex = mapViewModel.timeIndex.value,
                onDateSelected = { dt, index ->
                    mapViewModel.changePickupDate(dt, index)
                },
                onTimeSelected = { dt, index ->
                    mapViewModel.changePickupTime(
                        timeMillis = dt.timeMillis,
                        shift = dt.showToUser,
                        index = index
                    )
                }
            ) {
                mapViewModel.requestSchedule()
            }
        },
        sheetPeekHeight = ExtraBigSpacing,
        modifier = Modifier
            .padding(bottom = DrawerHeight)
            .fillMaxSize(),
        floatingActionButton = {
            currentLocation.latLang?.let { latLng ->
                FloatingActionButton(onClick = {
                    zoomState = 16f
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng, zoomState
                            )
                        )
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location",
                        tint = LightBg
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LogoWithText(
                    modifier = Modifier.fillMaxWidth(),
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
                Spacer(modifier = Modifier.size(BigSpacing))
                Box(
                    modifier = Modifier
                        .padding(horizontal = MediumSpacing)
                        .fillMaxWidth()
                        .aspectRatio(6.7f)
                ) {
                    SearchButton(
                        modifier = Modifier
                            .fillMaxSize(),
                        location = address
                    ) {
                        bottomNavController.navigate(
                            BottomScreens.SearchScreen.route
                                    + "?$LAT=${markerLocation.latitude}&$LON=${markerLocation.longitude}"
                        )
                    }
                    if (locationState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = ExtraSmallSpacing)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
            GoogleMap(
                properties = mapProperties,
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(-1f)
                    .alpha(0.75f),
            ) {
                destinationLatLng?.let { latLng ->
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(latLng, zoomState)
                        )
                    }
                }
                currentLocation.latLang?.let { latLng ->
                    Circle(
                        center = latLng,
                        fillColor = WeatherCardBgLowAlpha,
                        strokeColor = WeatherCardBgLowAlpha,
                        radius = 2500.0,
                    )
                }
                Marker(position = markerLocation)
            }

            if (showLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(101f))
            }
        }
    }
}

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    location: String = "",
    onSearchClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(MediumSpacing),
        elevation = ExtraSmallSpacing
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onSearchClick()
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_leading_serach_icon),
                contentDescription = "Leading Icon",
                modifier = Modifier
                    .padding(
                        start = SmallSpacing,
                        end = BigSpacing
                    )
                    .size(IconSize)
                    .padding(ExtraSmallSpacing),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            if (location.isBlank()) {
                Text(
                    text = "Search for places...",
                    style = MaterialTheme.typography.body2
                )
            } else {
                Text(
                    text = location,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomDialogueForMap(
    modifier: Modifier = Modifier,
    address: String = "",
    dateSelectedIndex: Int,
    timeSelectedIndex: Int,
    onDateSelected: (LocalDate, Int) -> Unit,
    onTimeSelected: (DropDownData, Int) -> Unit,
    onPickupButtonClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background,
        elevation = ExtraSmallSpacing
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ExtraBigSpacing,
                    vertical = MediumSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(SmallSpacing))
            PickupTextField(
                leadingIcon = R.drawable.ic_carbon_map,
                placeHolder = "Confirm your address",
                startText = address,
                modifier = Modifier
                    .padding(horizontal = BigSpacing)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            PickupDropDown(
                dropDown = getNextPickupDays(),
                selectedIndex = dateSelectedIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BigSpacing),
                onDropDownItemClick = { dt, index ->
                    onDateSelected(dt.localDate, index)
                }
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            PickupDropDown(
                dropDown = timeAvailable,
                selectedIndex = timeSelectedIndex,
                leadingIcon = R.drawable.ic_clock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BigSpacing),
                onDropDownItemClick = { dt, index ->
                    onTimeSelected(dt, index)
                }
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            Button(
                onClick = {
                    onPickupButtonClick()
                },
                shape = RoundedCornerShape(MediumSpacing),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightBg,
                    contentColor = MainBg
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = ExtraSmallSpacing
                ),
                contentPadding = PaddingValues(ExtraSmallSpacing)
            ) {
                Text(
                    text = "Request Pickup Vehicle",
                    style = MaterialTheme.typography.body1,
                    color = MainBg,
                    modifier = Modifier.padding(
                        horizontal = SmallSpacing,
                        vertical = ExtraSmallSpacing
                    )
                )
            }
        }
    }
}

@Composable
fun PickupTextField(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    placeHolder: String,
    startText: String = "",
) {
    var text by remember {
        mutableStateOf(startText)
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(MediumSpacing),
        elevation = BigStroke,
        backgroundColor = MaterialTheme.colors.background
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeHolder,
                    style = MaterialTheme.typography.body2
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MainBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = LightBg
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
//                onSearchClick(text)
            }),
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "Leading Icon",
                    modifier = Modifier
                        .padding(
                            start = SmallSpacing,
                            end = BigSpacing
                        )
                        .size(IconSize)
                        .padding(ExtraSmallSpacing),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }
        )
    }
}