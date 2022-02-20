package com.nipun.oceanbin.firsttime_display.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nipun.oceanbin.R
import com.nipun.oceanbin.ui.theme.*

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
            color = Color.White
        )
    }
}

@Composable
fun SecondSlide(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.15f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_message_dialog),
                contentDescription = "Message Dialogue",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(.85f)
                    .align(Alignment.TopStart),
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopStart
            )
            Image(
                painter = painterResource(id = R.drawable.ic_man),
                contentDescription = "Man",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(.75f)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop,
                alignment = Alignment.BottomEnd
            )
        }
        Spacer(modifier = Modifier.size(BigSpacing))
        Text(
            text = buildAnnotatedString {
                append("Request & Schedule\n\n")
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
                append("BIN Pickup Vehicle\n\nthrough the App")
            },
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Start,
            color = Color.White
        )
    }
}

@Composable
fun ThirdSlide(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_vehical),
            contentDescription = "Vehicle image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.15f),
            contentScale = ContentScale.Fit,
            alignment = Alignment.CenterStart
        )
        Spacer(modifier = Modifier.size(BigSpacing))
        Text(
            text = buildAnnotatedString {
                append("Pickup Arrives & Collects\n\n")
                withStyle(
                    style = SpanStyle(
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MediumTextSize,
                        color = Color.White
                    )
                ) {
                    append("Plastic Waste")
                }
            },
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Start,
            color = Color.White
        )
    }
}

@Composable
fun FourthSlide(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_successfull_transfer),
                contentDescription = "Vehicle image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.9f),
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopCenter
            )
            Spacer(modifier = Modifier.size(BigSpacing))
            Text(
                text = buildAnnotatedString {
                    append("Money gets credited to your\n\n")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = RobotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = MediumTextSize,
                            color = Color.White
                        )
                    ) {
                        append("Digital Wallet")
                    }
                },
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
        Button(
            onClick = { onContinueClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            shape = CircleShape,
            contentPadding = PaddingValues(
                vertical = ExtraSmallSpacing,
                horizontal = ExtraBigSpacing
            ),
            elevation = ButtonDefaults.elevation(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.button,
                color = Color.Black,
                modifier = Modifier.padding(
                    horizontal = SmallSpacing
                )
            )
        }
    }
}