package com.nipun.oceanbin.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.IconSize
import com.nipun.oceanbin.ui.theme.RobotoFamily

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
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("OCEAN")
                }
                withStyle(
                    style = SpanStyle(
                        color = color2,
                        fontFamily = RobotoFamily,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("BIN")
                }
            }
        )
    }
}