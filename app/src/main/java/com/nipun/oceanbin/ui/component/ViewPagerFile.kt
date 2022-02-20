package com.nipun.oceanbin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.BigSpacing
import com.nipun.oceanbin.ui.theme.LogoSplashSize
import com.nipun.oceanbin.ui.theme.MediumTextSize
import com.nipun.oceanbin.ui.theme.RobotoFamily

@Composable
fun FirstSlide(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_boat),
            contentDescription = "Boat image",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.size(BigSpacing))
        Text(
            text = buildAnnotatedString {
                append("Collect  ")
                withStyle(
                    style = SpanStyle(
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MediumTextSize,
                        color = Color.White
                    )
                ) {
                    append("PLASTIC WASTES\n\n")
                }
                append("on your next trip to the\n\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MediumTextSize,
                        color = Color.White
                    )
                ) {
                    append("OCEAN")
                }
                append(" or ")
                withStyle(
                    style = SpanStyle(
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MediumTextSize,
                        color = Color.White
                    )
                ) {
                    append("RIVER")
                }
            },
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Start,
        )
    }
}