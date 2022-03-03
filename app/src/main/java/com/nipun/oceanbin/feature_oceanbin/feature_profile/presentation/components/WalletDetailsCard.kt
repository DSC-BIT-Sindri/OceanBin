package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.*

@Composable
fun WalletDetailsCard(
    modifier: Modifier = Modifier,
    balance: Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Wallet",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(horizontal = BigSpacing)
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        Row(
            modifier
                .padding(
                    IconSize,
                    MediumSpacing
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = "Wallet Balance",
//                        modifier.size(5.dp)
                )
                Spacer(modifier = Modifier.size(MediumSpacing))
                Text(
                    text = "Current Balance",
                    fontFamily = RobotoFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            Text(
                text = "Rs. $balance",
                fontFamily = RobotoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

    }
}