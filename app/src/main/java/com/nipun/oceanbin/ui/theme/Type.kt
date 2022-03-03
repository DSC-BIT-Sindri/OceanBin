package com.nipun.oceanbin.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nipun.oceanbin.R

val RobotoFamily = FontFamily(
    Font(R.font.robot_black, FontWeight.Black),
    Font(R.font.robot_bold, FontWeight.Bold),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_body2, FontWeight.Light),
    Font(R.font.roboto_thin, FontWeight.Thin)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = LargeTextSize,
        color = Color.Black
    ),
    h3 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = MediumTextSize,
        color = Color.Black
    ), h4 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = SmallTextSize,
        color = Color.Black
    ),
    body2 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = MediumTextSize
    ),
    subtitle2 = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = BodyTextSize
    ),
    button = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Medium,
        fontSize = BodyTextSize,
        color = Color.White
    ),
    overline = TextStyle(
        fontFamily = RobotoFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        color = Color.Gray
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)