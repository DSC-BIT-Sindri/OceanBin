package com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.Constant.NEWS
import com.nipun.oceanbin.core.getMainScreenCurve
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.feature_oceanbin.BottomScreens
import com.nipun.oceanbin.feature_oceanbin.feature_home.presentation.HomeViewModel
import com.nipun.oceanbin.feature_oceanbin.feature_news.presentation.components.NewsDetails
import com.nipun.oceanbin.ui.theme.*
import java.net.URLEncoder

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BoxContents(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    expanded: Boolean,
    onDrag: (Int) -> Unit,
    onClick: () -> Unit = {}
) {
    val newsState = homeViewModel.newsState.value
    val news = newsState.data
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        if(newsState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(100f)
            )
        }
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val lazyListState = rememberLazyListState()
            onDrag(lazyListState.firstVisibleItemScrollOffset)
            Spacer(modifier = Modifier.size(MediumSpacing))
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                modifier = Modifier
                    .padding(top = SmallSpacing)
                    .size(IconSize)
                    .rotate(if (expanded) 90f else -90f)
                    .noRippleClickable {
                        onClick()
                    },
                contentDescription = "Down",
                tint = LightBg
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .padding(bottom = DrawerHeight)
                        .fillMaxHeight()
                ) {
                    item { Spacer(modifier = Modifier.size(DrawerHeight)) }
                    if (news.isNotEmpty()) {
                        item {
                            Text(
                                text = "NEWS",
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(start = IconSize)
                            )
                            Spacer(modifier = Modifier.height(MediumSpacing))
                            val pagerState = rememberPagerState(
                                pageCount = news.size,
                                initialOffscreenLimit = 1,
                                infiniteLoop = false,
                                initialPage = if (news.size > 1) 1 else 0
                            )
                            // A global coroutine for performing async task in composable function
                            val coroutineScope = rememberCoroutineScope()
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = MediumSpacing)
                            ) { index ->
                                val newsDetail = news[index]
                                SingleCard(
                                    newsDetails = newsDetail,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = MediumSpacing,
                                            vertical = SmallSpacing
                                        )
                                        .fillMaxWidth(0.6f)
                                        .aspectRatio(0.8f)
                                ){
                                    val gson = Gson()
                                    val param = URLEncoder.encode(
                                        gson.toJson(newsDetail),"utf-8"
                                    )
                                    bottomNavController.navigate(BottomScreens.NewsScreen.route+"?$NEWS=$param"){
                                        popUpTo(BottomScreens.HomeScreen.route)
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(SmallSpacing)
                        .animateContentSize()
                        .graphicsLayer {
                            translationY = if (lazyListState.firstVisibleItemIndex == 1) {
                                lazyListState.firstVisibleItemScrollOffset * 1.2f
                            } else if (lazyListState.firstVisibleItemIndex == 0) 0f else 100000f
                        },
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
                    Spacer(modifier = Modifier.size(SmallSpacing))
                }

            }
        }
    }
}

@Composable
fun SingleCard(
    modifier: Modifier = Modifier,
    newsDetails: NewsDetails,
    onMoreClick : () -> Unit
) {
    Surface(
        modifier = modifier,
        elevation = SmallSpacing,
        shape = RoundedCornerShape(MediumSpacing)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val painter = rememberImagePainter(newsDetails.image) {
                placeholder(R.drawable.test)
            }
            Image(
                painter = if (newsDetails.image.isBlank()) painterResource(id = R.drawable.test) else painter,
                contentDescription = newsDetails.heading,
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
                    text = newsDetails.heading,
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
                        .clickable {
                            onMoreClick()
                        }
                        .clip(CircleShape)
                        .background(LightBg)
                        .padding(ExtraSmallSpacing),
                    tint = MainBg
                )
            }
        }
    }
}