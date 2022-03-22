package com.nipun.oceanbin.feature_oceanbin.feature_map.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nipun.oceanbin.R
import com.nipun.oceanbin.feature_oceanbin.feature_map.local.DropDownData
import com.nipun.oceanbin.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
val timeAvailable = listOf(
    DropDownData(
        showToUser = "Pick Time"
    ),
    DropDownData(
        showToUser = "09:00 AM - 10:00 AM",
        timeMillis = 32400L
    ),
    DropDownData(
        showToUser = "10:00 AM - 11:00 AM",
        timeMillis = 57600
    )
)



@Composable
fun PickupDropDown(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    dropDown: List<DropDownData>,
    leadingIcon: Int = R.drawable.ic_date_picker,
    onDropDownItemClick: (DropDownData, Int) -> Unit
) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(SmallSpacing),
        color = Color.White,
        elevation = ExtraSmallSpacing
    ) {
        if (dropDown.isNotEmpty()) {
            ComposeMenu(
                modifier = Modifier
                    .clickable {
                        menuExpanded = true
                    }
                    .padding(SmallSpacing)
                    .fillMaxWidth(),
                menuItems = dropDown,
                leadingIcon = leadingIcon,
                menuExpandedState = menuExpanded,
                selectedIndex = selectedIndex,
                onDismissMenuView = {
                    menuExpanded = false
                },
                onMenuItemClick = {
                    menuExpanded = false
                    onDropDownItemClick(dropDown[it], it)
                }
            )
        }
    }
}

@Composable
fun ComposeMenu(
    menuItems: List<DropDownData>,
    menuExpandedState: Boolean,
    selectedIndex: Int,
    onDismissMenuView: () -> Unit,
    leadingIcon: Int,
    onMenuItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "Leading Icon",
                    modifier = Modifier
                        .size(IconSize)
                        .padding(ExtraSmallSpacing),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.size(SmallSpacing))
                Text(
                    text = menuItems[selectedIndex].showToUser,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.size(SmallSpacing))
            Icon(
                painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
                contentDescription = "dropDown",
                modifier = Modifier
                    .size(IconSize)
                    .padding(ExtraSmallSpacing)
                    .rotate(if (menuExpandedState) 180f else 0f)
            )
        }
        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .background(MaterialTheme.colors.background)
        ) {
            menuItems.forEachIndexed { index, dropDownData ->
                DropdownMenuItem(
                    onClick = {
                        if (index >= 0) {
                            onMenuItemClick(index)
                        }
                    }) {
                    Text(
                        text = dropDownData.showToUser,
                        style = MaterialTheme.typography.body2,
                        color = if(index == selectedIndex) LightBg else Color.Black
                    )
                }
            }
        }
    }
}
