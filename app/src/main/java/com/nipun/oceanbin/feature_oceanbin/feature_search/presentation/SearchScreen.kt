package com.nipun.oceanbin.feature_oceanbin.feature_search.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.Constant.LAT
import com.nipun.oceanbin.core.Constant.LON
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.feature_oceanbin.BottomScreens
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.HorizontalLine
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    bottomNavController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchState = searchViewModel.searchState.value
    val searchResults = searchState.data
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(
        key1 = true
    ) {
        searchViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SmallSpacing)
                    .height(IntrinsicSize.Min),
                text = searchViewModel.searchQuery.value,
                onValueChange = {
                    searchViewModel.searchLocation(it)
                },
                onBackClick = {
                    bottomNavController.navigateUp()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (searchState.isLoading) {
                Log.e("Search","Loading")
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(10f)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumSpacing),
                contentPadding = PaddingValues(SmallSpacing)
            ) {
                items(searchResults.size) { index ->
                    val searchModel = searchResults[index]
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .clickable {
                                bottomNavController.navigate(BottomScreens.MapScreen.route
                                +"?$LAT=${searchModel.latLng.latitude}&$LON=${searchModel.latLng.longitude}"){
                                    popUpTo(BottomScreens.HomeScreen.route)
                                }
                            }
                    ) {
                        Text(
                            text = searchModel.getName(),
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = searchModel.addressLine ?: "",
                            style = MaterialTheme.typography.subtitle2
                        )
                        Spacer(modifier = Modifier.size(ExtraSmallSpacing))
                        HorizontalLine(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    text: String = "",
    onBackClick: () -> Unit,
    onValueChange: (String) -> Unit = {}
) {
    val focusRequester = remember {
        FocusRequester()
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(MediumSpacing),
        elevation = ExtraSmallSpacing
    ) {
        TextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            shape = RoundedCornerShape(SmallSpacing),
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
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
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Leading Icon",
                    modifier = Modifier
                        .noRippleClickable {
                            onBackClick()
                        }
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

    LaunchedEffect(
        key1 = true,
        block = {
            focusRequester.requestFocus()
        }
    )
}