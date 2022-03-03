package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.ui.theme.*

@Composable
fun PersonalDetails(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Personal Details",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(horizontal = BigSpacing)
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Full Name",
            value = "Nipun Kumar"
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Email",
            value = "nipunvirat0@gmail.com"
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Mobile Number",
            value = "9065814864"
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Password",
            value = "nipunafdak"
        )
    }
}

@Composable
fun SingleDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = ""
) {
    Card(
        modifier = modifier,
        elevation = ExtraSmallSpacing
    ) {
        Column(modifier = modifier) {
            Text(
                text = title,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .padding(start = MediumSpacing)
            )
            SingleDetailsEdit(
                modifier = Modifier
                    .fillMaxWidth(),
                placeHolder = title,
                initialValue = value
            )

        }
    }
}

@Composable
fun SingleDetailsEdit(
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    initialValue: String = ""
) {
    var text by remember {
        mutableStateOf(initialValue)
    }
    var enable by remember {
        mutableStateOf(false)
    }
    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = modifier,
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.body2
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
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
        readOnly = enable,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Leading Icon",
                modifier = Modifier
                    .size(IconSize)
                    .padding(ExtraSmallSpacing)
                    .noRippleClickable {
                        enable = !enable
                    },
            )
        }
    )
}