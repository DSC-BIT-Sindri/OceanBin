package com.nipun.oceanbin.feature_oceanbin.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.nipun.oceanbin.ui.theme.MediumSpacing
import com.nipun.oceanbin.ui.theme.RobotoFamily
import com.nipun.oceanbin.ui.theme.SmallSpacing

@Composable
fun WalletDetailsCard(
    modifier : Modifier = Modifier.fillMaxWidth(),
    balance : Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Wallet",
            fontFamily = RobotoFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(MediumSpacing))
        Card(
            modifier = modifier,
            elevation = SmallSpacing,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier.padding(15.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.ic_coin),
                        contentDescription = "Wallet Balance"
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
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}