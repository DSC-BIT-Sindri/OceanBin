package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    navController: NavController,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val locationState = mapViewModel.location.value
    val location = locationState.data.latLang
    val mapState = mapViewModel.mapState.value
    val address = locationState.data.address
    var zoomState by remember {
        mutableStateOf(11f)
    }

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
            location,
            size
        )
    }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    LaunchedEffect(key1 = locationState) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(
                location, zoomState
            )
        )
    }
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false
        )
    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomDialogueForMap(
                modifier = Modifier
                    .fillMaxWidth(),
                address = address
            )
        },
        sheetPeekHeight = ExtraBigSpacing,
        modifier = Modifier
            .padding(bottom = DrawerHeight)
            .fillMaxSize(),
        floatingActionButton = {
            mapState.data.latLang.let { currentLocation ->
                FloatingActionButton(onClick = {
                    zoomState = 16f
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLocation, zoomState
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
                    SearchBox(
                        modifier = Modifier
                            .fillMaxSize(),
                        location = address
                    ) {
                        mapViewModel.searchLocation(it)
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
                properties = mapState.mapProperties,
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(-1f)
                    .alpha(0.75f),
            ) {
                mapState.data.latLang.let { currentLocation ->
                    Circle(
                        center = currentLocation,
                        fillColor = WeatherCardBgLowAlpha,
                        strokeColor = WeatherCardBgLowAlpha,
                        radius = 2500.0,
                    )
                }
                Marker(position = location)
            }
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    location : String = "",
    onSearchClick: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf(location)
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(MediumSpacing),
        elevation = ExtraSmallSpacing
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            shape = RoundedCornerShape(SmallSpacing),
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(
                    text = "Search for places",
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
                onSearchClick(text)
            }),
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
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
            }
        )
    }
}

@Composable
fun BottomDialogueForMap(
    modifier: Modifier = Modifier,
    address : String = ""
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background,
        elevation = ExtraSmallSpacing
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SmallSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PickupTextField(
                leadingIcon = R.drawable.ic_carbon_map,
                placeHolder = "Confirm your address",
                startText = address,
                modifier = Modifier
                    .padding(horizontal = BigSpacing)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            PickupTextField(
                leadingIcon = R.drawable.ic_clock,
                placeHolder = "Time",
                modifier = Modifier
                    .padding(horizontal = BigSpacing)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(MediumSpacing))
            PickupTextField(
                leadingIcon = R.drawable.ic_date_picker,
                placeHolder = "Date",
                modifier = Modifier
                    .padding(horizontal = BigSpacing)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(ExtraBigSpacing))
            Button(
                onClick = { },
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
                    modifier = Modifier.padding(horizontal = SmallSpacing, vertical = ExtraSmallSpacing)
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
    startText : String = "",
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