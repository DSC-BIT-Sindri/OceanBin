package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nipun.oceanbin.R
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components.HomeScreenContent
import com.nipun.oceanbin.ui.theme.LightBg
import com.nipun.oceanbin.ui.theme.MainBg

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val showPermissionDialogue = homeViewModel.shouldShowRational.value
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    if (permissionState.allPermissionsGranted) {
        homeViewModel.setHasPermission()
        homeViewModel.getLocation()
        HomeScreenContent(navController = navController, homeViewModel)
    } else {
        if (homeViewModel.shouldShowRational.value) {
            PermissionsRequired(
                multiplePermissionsState = permissionState,
                permissionsNotGrantedContent = {
                    if (showPermissionDialogue && !permissionState.shouldShowRationale) {
                        ShowPermissionDialogue(onYesClick = {
                            permissionState.launchMultiplePermissionRequest()
                        },
                            onCancelClick = {
                                homeViewModel.setPermanentlyDenied()
                            })
                    } else if (permissionState.shouldShowRationale) {
                        PermissionPermanentlyDenied(onOpenSettingsClick = {
                            permissionState.launchMultiplePermissionRequest()
                            homeViewModel.setPermanentlyDenied()
                        }, onCancelClick = {
                            homeViewModel.setPermanentlyDenied()
                        })
                    } else {
                        homeViewModel.setHasPermission(false)
                        homeViewModel.setCount()
                        homeViewModel.getLocation()
                        HomeScreenContent(navController = navController, homeViewModel)
                    }
                },
                permissionsNotAvailableContent = {
                    homeViewModel.setPermanentlyDenied()
                    homeViewModel.setHasPermission(false)
                    homeViewModel.setCount()
                    HomeScreenContent(navController = navController, homeViewModel)
                }) {
            }
        }else{
            Log.e("NipunL","Set not show rational")
            homeViewModel.setHasPermission(false)
            homeViewModel.setCount()
            HomeScreenContent(navController = navController,homeViewModel)
        }
    }
}

@Composable
fun ShowPermissionDialogue(
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = stringResource(id = R.string.location_permission),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.show_location_quote),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onYesClick()
                    }
                ) {
                    Text("OK", color = Color.White)
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

@Composable
fun PermissionPermanentlyDenied(
    onOpenSettingsClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val openDialog = remember {
        mutableStateOf(true)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.location_service),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h2,
                        color = MainBg
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.setting_location_dialogue),
                        style = MaterialTheme.typography.body1,
                        color = MainBg
                    )
                },
                contentColor = MainBg,
                backgroundColor = LightBg,
                confirmButton = {
                    TextButton(onClick = {
                        onOpenSettingsClick()
                        openDialog.value = false
                    }) {
                        Text(
                            text = "Open Setting",
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            color = MainBg
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        onCancelClick()
                        openDialog.value = false
                    }) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
            )
        }
    }
}