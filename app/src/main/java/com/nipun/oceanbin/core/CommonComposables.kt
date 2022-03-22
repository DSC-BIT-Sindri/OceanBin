package com.nipun.oceanbin.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.*
import java.security.Permission

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onAction) {
                            Text(
                                text = actionLabel,
                                color = SnackbarDefaults.primaryActionColor,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Composable
fun LogoWithText(
    modifier: Modifier = Modifier,
    color1: Color = Color.White,
    color2: Color = Color.White
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_adaptive_fore),
            contentDescription = "Logo",
            modifier = Modifier.size(IconSize),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = color1,
                        fontFamily = RobotoFamily,
                        fontSize = LargeTextSize,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("OCEAN")
                }
                withStyle(
                    style = SpanStyle(
                        color = color2,
                        fontFamily = RobotoFamily,
                        fontSize = LargeTextSize,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("BIN")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PermissionUi(
    context: Context,
    permission: String,
    permissionRational: String,
    permanentDenyMessage: String,
    permanentDeny: Boolean = false,
    scaffoldState: BottomSheetScaffoldState,
    permissionAction: (PermissionAction) -> Unit
) {
    val permissionGranted = context.checkIfPermissionGranted(permission)
    if (permissionGranted) {
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                permissionAction(PermissionAction.OnPermissionGranted)
            } else {
                permissionAction(PermissionAction.OnPermissionDenied)
            }
        }
    )
    val showRational = context.checkShouldShowPermissionRational(permission)
    when {
        showRational -> {
            LaunchedEffect(
                key1 = showRational
            ) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = permissionRational,
                    actionLabel = "Grant Access",
                    duration = SnackbarDuration.Long
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                    }
                    SnackbarResult.ActionPerformed -> {
                        permissionAction(PermissionAction.OnPreviouslyDenied)
                        launcher.launch(permission)
                    }
                }
            }
        }
        permanentDeny && !showRational -> {
            LaunchedEffect(
                key1 = permanentDeny
            ) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = permanentDenyMessage,
                    actionLabel = "Open setting",
                    duration = SnackbarDuration.Long
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                    }
                    SnackbarResult.ActionPerformed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            val uri = Uri.fromParts("package", context.packageName, null)
                            data = uri
                            context.startActivity(this)
                        }
                    }
                }
            }
        }
        else -> {
            LaunchedEffect(key1 = permissionGranted) {
                launcher.launch(permission)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MultiplePermissionUi(
    context: Context,
    permission: List<String>,
    permissionRational: String,
    permanentDenyMessage: String,
    permanentDeny: Boolean = false,
    scaffoldState: BottomSheetScaffoldState,
    permissionAction: (PermissionAction) -> Unit
) {
    val permissionGranted = context.checkMultiplePermissionGranted(permission)
    if (permissionGranted) {
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionUri ->
            var granted = true
            permission.forEach { perm ->
                if (permissionUri[perm] != true) {
                    granted = false
                }
            }
            if (granted) {
                permissionAction(PermissionAction.OnPermissionGranted)
            } else {
                permissionAction(PermissionAction.OnPermissionDenied)
            }
        }
    )
    val showRational = context.checkMultipleShouldShowRational(permission)
    when {
        showRational -> {
            LaunchedEffect(
                key1 = showRational
            ) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = permissionRational,
                    actionLabel = "Grant Access",
                    duration = SnackbarDuration.Long
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                    }
                    SnackbarResult.ActionPerformed -> {
                        launcher.launch(permission.toTypedArray())
                    }
                }
            }
        }
        permanentDeny -> {
            LaunchedEffect(
                key1 = permanentDeny
            ) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = permanentDenyMessage,
                    actionLabel = "Open setting",
                    duration = SnackbarDuration.Long
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                    }
                    SnackbarResult.ActionPerformed -> {
                        permissionAction(PermissionAction.OnPermissionDenied)
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            val uri = Uri.fromParts("package", context.packageName, null)
                            data = uri
                            context.startActivity(this)
                        }
                    }
                }
            }
        }
        else -> {
            LaunchedEffect(key1 = permissionGranted) {
                launcher.launch(permission.toTypedArray())
            }
        }
    }
}

@Composable
fun CustomAlertDialogue(
    title: Int,
    text: Int,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onCancelClick()
        },
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h3,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
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
        contentColor = MainBg,
        shape = RoundedCornerShape(SmallSpacing)
    )
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    title: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                onClick()
            },
            shape = RoundedCornerShape(MediumSpacing),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LightBg,
                contentColor = MainBg
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = ExtraSmallSpacing
            ),
            modifier = Modifier
                .padding(
                    vertical = ExtraSmallSpacing,
                    horizontal = SmallSpacing
                ),
            contentPadding = PaddingValues(ExtraSmallSpacing),
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(IconSize)
                        .padding(ExtraSmallSpacing)
                )
                Spacer(modifier = Modifier.size(SmallSpacing))
            }
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.body1,
                color = MainBg,
                modifier = Modifier.padding(
                    SmallSpacing
                )
            )
        }
    }
}

sealed class PermissionAction {

    object OnPermissionGranted : PermissionAction()

    object OnPermissionDenied : PermissionAction()

    object OnPreviouslyDenied : PermissionAction()
}