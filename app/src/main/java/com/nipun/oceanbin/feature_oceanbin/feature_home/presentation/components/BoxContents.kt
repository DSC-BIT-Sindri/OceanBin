package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BoxContents(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        ) {
            drawPath(
                getMainScreenCurve(width, height),
                color = ShadowColor,
                style = Stroke(width = 8f)
            )
            drawPath(
                getMainScreenCurve(width, height),
                color = MainBg,
            )
        }
        LazyColumn(
            modifier = modifier
                .padding(top = CurveHeight)
        ) {
            item {
                Text(
                    text = "NEWS",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = IconSize)
                )
                Spacer(modifier = Modifier.height(MediumSpacing))
                val pagerState = rememberPagerState(
                    pageCount = 4,
                    initialOffscreenLimit = 1,
                    infiniteLoop = false,
                    initialPage = 1
                )
                // A global coroutine for performing async task in composable function
                val coroutineScope = rememberCoroutineScope()
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SingleCard(
                        modifier = Modifier
                            .padding(horizontal = MediumSpacing)
                            .fillMaxWidth(0.6f)
                            .aspectRatio(0.8f)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SmallSpacing)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Request",
                style = MaterialTheme.typography.h4,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(ExtraSmallSpacing))
            Button(
                onClick = { },
                shape = RoundedCornerShape(MediumSpacing),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightBg,
                    contentColor = MainBg
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = ExtraSmallSpacing
                )
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MainBg,
                                fontFamily = RobotoFamily,
                                fontSize = MediumTextSize,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("OCEAN")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MainBg,
                                fontFamily = RobotoFamily,
                                fontSize = MediumTextSize,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("BIN")
                        }
                        append(" Pickup Vehicle")
                    },
                    style = MaterialTheme.typography.h3,
                    color = MainBg,
                    modifier = Modifier.padding(
                        horizontal = BigSpacing,
                        vertical = ExtraSmallSpacing
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.size(DrawerHeight))
        }
    }
}

@Composable
fun SingleCard(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        elevation = SmallSpacing,
        shape = RoundedCornerShape(MediumSpacing)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.test),
                contentDescription = "Test",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.65f)
                    .clip(RoundedCornerShape(MediumSpacing))
                    .background(color = MainBg)
                    .padding(MediumSpacing)
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Israeli man grows heaviest strawberry weighing 289 gms," +
                            "breaks world record\n" +
                            "Israeli man grows heaviest strawberry weighing 289 gms,\" +\n" +
                            "                            \"breaks world record\\n\"" +
                            "Israeli man grows heaviest strawberry weighing 289 gms,\" +\n" +
                            "                            \"breaks world record\\n\"",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxHeight(0.7f),
                    overflow = TextOverflow.Clip
                )
                Spacer(modifier = Modifier.size(ExtraSmallSpacing))
                Icon(
                    painter = painterResource(id = R.drawable.ic_next_one),
                    contentDescription = "Next",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(LightBg)
                        .padding(ExtraSmallSpacing),
                    tint = MainBg
                )
            }
        }
    }
}