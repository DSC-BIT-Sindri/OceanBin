package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.firebase.User
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextChangeEvent
import com.nipun.oceanbin.firsttime_display.feature_register.presntation.state.TextState
import com.nipun.oceanbin.ui.theme.BigSpacing
import com.nipun.oceanbin.ui.theme.ExtraSmallSpacing
import com.nipun.oceanbin.ui.theme.IconSize
import com.nipun.oceanbin.ui.theme.MediumSpacing

@Composable
fun PersonalDetails(
    modifier: Modifier = Modifier,
    user: User,
    nameState: TextState,
    phoneState: TextState,
    onNameEditClick: () -> Unit,
    onChangePasswordClick : () -> Unit,
    onPhoneEditClick: () -> Unit
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
            value = nameState.text,
            isShowEdit = true,
            onEditClick = { onNameEditClick() }
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Email",
            value = user.email
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Mobile Number",
            value = phoneState.text,
            isShowEdit = true,
            onEditClick = { onPhoneEditClick() }
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        SingleDetail(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Password",
            value = "*****************",
            isShowEdit = true,
            onEditClick = {onChangePasswordClick()}
        )
    }
}

@Composable
fun SingleDetail(
    modifier: Modifier = Modifier,
    title: String = "",
    value: String = "",
    isShowEdit: Boolean = false,
    onEditClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = ExtraSmallSpacing
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .padding(start = MediumSpacing)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        MediumSpacing
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth(0.87f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isShowEdit) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Leading Icon",
                        modifier = Modifier
                            .size(IconSize)
                            .padding(ExtraSmallSpacing)
                            .noRippleClickable {
                                onEditClick()
                            },
                    )
                }
            }
        }
    }
}