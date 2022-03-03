package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import android.service.quickaccesswallet.WalletCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.ui.theme.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.PersonalDetails
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.RecycledWasteCard
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.WalletDetailsCard
import org.intellij.lang.annotations.JdkConstants

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ProfileScreenDetails(modifier)
}

@Preview
@Composable
fun ProfileScreenDetails(
    modifier: Modifier = Modifier
) {
    Scaffold(
        backgroundColor = TopbarLightBlue,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = BigSpacing)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LogoWithText(
                    modifier,
                    color1 = LogoDarkBlue,
                    color2 = LightBgShade
                )
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
                        painter = painterResource(id = R.drawable.ic_profile),
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
            }
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.755f)
                    .align(Alignment.BottomCenter)
                    .zIndex(-1f),
                shape = RoundedCornerShape(BigSpacing)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = BigSpacing)
                ) {
                    Spacer(modifier = Modifier.height(DrawerHeight))
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(vertical = BigSpacing)
                    ) {
                        item {
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
                        }
                        item {
                            Spacer(modifier = Modifier.size(MediumSpacing))
                            PersonalDetails(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = SmallSpacing
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}





