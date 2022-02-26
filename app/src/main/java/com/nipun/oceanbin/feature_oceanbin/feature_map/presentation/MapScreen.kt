package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.PreferenceManager
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.ui.theme.*

@Composable
fun MapScreen(
    navController: NavController
) {
    val location = PreferenceManager(LocalContext.current).getCurrentLocation()
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoomPreference = 20f, minZoomPreference = 2f)
        )
    }
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 11f)
    }
    Box(
        modifier = Modifier
            .padding(bottom = DrawerHeight)
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
            SearchBox(
                modifier = Modifier
                    .padding(horizontal = MediumSpacing)
                    .fillMaxWidth()
                    .height(DrawerHeight)
            )
        }
        GoogleMap(
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .alpha(0.75f)
        ) {
            Marker(position = location)
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = "Location",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(SmallSpacing)
                .size(DrawerHeight)
                .clip(CircleShape)
                .background(color = MainBg)
                .padding(SmallSpacing)
                .noRippleClickable {
                    cameraPositionState.move(
                        CameraUpdateFactory.zoomTo(16f)
                    )
                },
            tint = LightBg
        )
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
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
            textStyle = MaterialTheme.typography.h4,
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