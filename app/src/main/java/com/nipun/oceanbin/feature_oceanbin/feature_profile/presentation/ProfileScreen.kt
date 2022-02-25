package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation

import android.service.quickaccesswallet.WalletCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.ui.theme.*
import com.nipun.oceanbin.R
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.RecycledWasteCard
import com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components.WalletDetailsCard
import org.intellij.lang.annotations.JdkConstants

@Composable
fun ProfileScreen(
    modifier : Modifier = Modifier,
    navController: NavController
) {
    ProfileScreenDetails(modifier)
}

@Preview
@Composable
fun ProfileScreenDetails(
    modifier: Modifier = Modifier
){
    Scaffold(backgroundColor = TopbarLightBlue) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(BigSpacing))
            LogoWithText(
                modifier,
                color1 = LogoDarkBlue,
                color2 = LightBgShade
            )
            Spacer(modifier = Modifier.size(CurveHeight))
            Card(
                modifier = modifier
                    .weight(2f),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    RecycledWasteCard(weight = 75)
                    Spacer(modifier = Modifier.size(MediumSpacing))
                    WalletDetailsCard(balance = 1000)
                    
                }
            }
        }
    }
}





