package com.nipun.oceanbin.firsttime_display

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.nipun.oceanbin.R
import com.nipun.oceanbin.core.LogoWithText
import com.nipun.oceanbin.core.noRippleClickable
import com.nipun.oceanbin.firsttime_display.component.*
import com.nipun.oceanbin.ui.Screen
import com.nipun.oceanbin.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashViewPager(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_viewpager_bg),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.77f)
                .zIndex(-1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // This composable function is responsible for showing splash screen
            SetupViewPager(
                modifier = Modifier.fillMaxSize()
            ) {
                /*
                 * This lambda block will execute when user clicked continue.
                 * When all page of view pager is checked and user clicked continue we set
                 * a preference that our app is visited first time buy simply put a boolean value
                 * in splash screen.
                 */
                navController.navigate(Screen.DoLoginSignup.route) {
                    popUpTo(Screen.SplashSeaViewPager.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun SplashBeachViewPager(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_viewpager_bg_beach),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.77f)
                .zIndex(-1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // This composable function is responsible for showing splash screen
            SetupBeachViewPager(
                modifier = Modifier.fillMaxSize()
            ) {
                /*
                 * This lambda block will execute when user clicked continue.
                 * When all page of view pager is checked and user clicked continue we set
                 * a preference that our app is visited first time buy simply put a boolean value
                 * in splash screen.
                 */
                navController.navigate(Screen.DoLoginSignup.route) {
                    popUpTo(Screen.SplashBeachViewPager.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupViewPager(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit
) {

    // Details of view pager
    val pagerState = rememberPagerState(
        pageCount = 4,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    // A global coroutine for performing async task in composable function
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            when (index) {
                0 -> {
                    FirstSlide(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    )
                }
                1 -> {
                    SecondSlide(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    )
                }
                2 -> {
                    ThirdSlide(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                3 -> {
                    FourthSlide(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    ) {
                        onContinueClick()
                    }
                }
            }
        }
        /* If pager current page index is not equal to 3, i.e pager is not in last index
         * We can show skip button but when pager reached last index, if block will not execute
         * And skip button will be hide automatically.
         */
        if (pagerState.currentPage != 3) {
            Text(
                text = "skip",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = SmallSpacing)
                    .noRippleClickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(
                                page = 3
                            )
                        }
                    },
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .padding(MediumSpacing)
                .fillMaxWidth()
                .height(40.dp)
                .align(Alignment.BottomCenter),
        ) {
            if (pagerState.currentPage != 0) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(IconSize)
                        .noRippleClickable {
                            coroutineScope.launch {
                                if (!pagerState.isScrollInProgress) {
                                    pagerState.scrollToPage(
                                        page = pagerState.currentPage - 1
                                    )
                                }
                            }
                        }
                        .rotate(180f),
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            if (pagerState.currentPage != 3) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(IconSize)
                        .noRippleClickable {
                            coroutineScope.launch {
                                if (!pagerState.isScrollInProgress) {
                                    pagerState.scrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        },
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Next",
                    tint = Color.White
                )
            }

            // This composable function is responsible to show dot indicator
            Dots(
                dotCount = 4, currentSelect = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .align(Alignment.Center)
            ) { index ->
                coroutineScope.launch {
                    if (!pagerState.isScrollInProgress) {
                        pagerState.scrollToPage(
                            page = index
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupBeachViewPager(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit
) {

    // Details of view pager
    val pagerState = rememberPagerState(
        pageCount = 3,
        initialOffscreenLimit = 1,
        infiniteLoop = false,
        initialPage = 0
    )

    // A global coroutine for performing async task in composable function
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            when (index) {
                0 -> {
                    FirstSlide1(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    )
                }
                1 -> {
                    SecondSlide2(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    )
                }
                2 -> {
                    ThirdSlide2(
                        modifier = Modifier
                            .padding(BigSpacing)
                            .fillMaxSize()
                    ) {
                        onContinueClick()
                    }
                }
            }
        }
        /* If pager current page index is not equal to 3, i.e pager is not in last index
         * We can show skip button but when pager reached last index, if block will not execute
         * And skip button will be hide automatically.
         */
        if (pagerState.currentPage != 2) {
            Text(
                text = "skip",
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = SmallSpacing)
                    .noRippleClickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(
                                page = 2
                            )
                        }
                    },
                color = Brown
            )
        }
        Box(
            modifier = Modifier
                .padding(MediumSpacing)
                .fillMaxWidth()
                .height(40.dp)
                .align(Alignment.BottomCenter),
        ) {
            if (pagerState.currentPage != 0) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(IconSize)
                        .noRippleClickable {
                            coroutineScope.launch {
                                if (!pagerState.isScrollInProgress) {
                                    pagerState.scrollToPage(
                                        page = pagerState.currentPage - 1
                                    )
                                }
                            }
                        }
                        .rotate(180f),
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            if (pagerState.currentPage != 2) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(IconSize)
                        .noRippleClickable {
                            coroutineScope.launch {
                                if (!pagerState.isScrollInProgress) {
                                    pagerState.scrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        },
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Next",
                    tint = Color.Black
                )
            }

            // This composable function is responsible to show dot indicator
            Dots(
                dotCount = 3, currentSelect = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .align(Alignment.Center)
            ) { index ->
                coroutineScope.launch {
                    if (!pagerState.isScrollInProgress) {
                        pagerState.scrollToPage(
                            page = index
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Dots(
    modifier: Modifier = Modifier,
    dotCount: Int,
    currentSelect: Int,
    onDotClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = scrollState) {
        scrollState.scrollTo(currentSelect)
    }
    Row(
        modifier = modifier
            .horizontalScroll(scrollState, true),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until dotCount) {
            Surface(
                modifier = Modifier
                    .size(SmallSpacing)
                    .alpha(if (i == currentSelect) 0.7f else 1f)
                    .noRippleClickable {
                        onDotClick(i)
                    },
                shape = CircleShape,
                color = if (i == currentSelect) Color.Gray else Color.White,
                elevation = (-1).dp
            ) {}
            Spacer(modifier = Modifier.width(MediumSpacing))
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isInstalled = mainViewModel.isInstalled.value
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightBg)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_screen),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.77f)
                .zIndex(-1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_adaptive_fore),
                contentDescription = "logo",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.size(LogoSplashSize)
            )
            Spacer(modifier = Modifier.size(SmallSpacing))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = DarkBlue,
                            fontFamily = RobotoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = SplashTextSize
                        )
                    ) {
                        append("OCEAN")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = LightBg,
                            fontFamily = RobotoFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = SplashTextSize
                        )
                    ) {
                        append("BIN")
                    }
                }
            )
        }
    }
    LaunchedEffect(key1 = true) {
        delay(1200L)
        if (isInstalled) {
//            navController.navigate(Screen.SplashViewPager.route) {
//                popUpTo(Screen.SplashScreen.route) {
//                    inclusive = true
//                }
//            }
            navController.navigate(Screen.WhoAreYouScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screen.BottomScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun WhoAreYouScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.who_are_you_bg),
            contentDescription = "Background Image",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,

            )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.border(width = 2.dp, color = Color.Black)
        ) {
            Text(
                "WHO ARE YOU", style = Typography.h1,
                modifier = Modifier.padding(top = 50.dp),
                color = Brown
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Whom(
                    navController,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 20.dp),
                    "VOLUNTEER",
                    painterResource(id = R.drawable.volunteer)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Whom(
                    navController,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                    "FISHERMAN",
                    painterResource(id = R.drawable.fisherman)
                )
            }
        }
    }
}

@Composable
fun Whom(
    navController: NavController,
    modifier: Modifier,
    identity: String,
    painter: Painter,
    spacerValue: Dp = 6.dp
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 16.dp, bottom = 16.dp, start = 5.dp)
    ) {
        Spacer(modifier = Modifier.height(spacerValue))
        Image(
            modifier = Modifier
                .width(145.dp)
                .height(400.dp), painter = painter, contentDescription = identity
        )
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(170.dp)
                .padding(top = 70.dp, start = 10.dp, end = 10.dp),
            onClick = {
                if (identity.equals("VOLUNTEER")) {
//                    navController.navigate(Screen.Home.route){
//                        popUpTo(Screen.Home.route){
//                            inclusive=true
//                        }
//                    }
                    navController.navigate(route = Screen.SplashBeachViewPager.route)
                } else if (identity.equals("FISHERMAN")) {
                    navController.navigate(route = Screen.SplashSeaViewPager.route)
                }
            },
            border = BorderStroke(1.dp, Brown),
            shape = RoundedCornerShape(50), // = 50% percent
            // or shape = CircleShape
            colors = ButtonDefaults.outlinedButtonColors(Color.White)
        ) {
            Text(text = identity, color = Brown)
        }

    }
}

@Composable
fun DoLoginSignup(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MediumSpacing),
            color1 = LogoDarkBlue,
            color2 = LightBgShade
        )
        Image(
            modifier = Modifier.weight(3f),
            painter = painterResource(id = R.drawable.ic_recycle_bottle),
            contentDescription = "DoRegister"
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(45.dp),
            onClick = {
                navController.navigate(Screen.Signup.route) {
                    popUpTo(Screen.SplashSeaViewPager.route) {
                        inclusive = true
                    }
                }
            },
            shape = RoundedCornerShape(BigSpacing),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LightBg,
                contentColor = MainBg
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = ExtraSmallSpacing
            ),
            contentPadding = PaddingValues(ExtraSmallSpacing)
        ) {
            Text(text = "Register", style = Typography.h3, color = Color.White)
        }
        Text(
            text = "Sign In",
            style = Typography.h3,
            color = Purple,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(top = 100.dp)
                .clickable {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SplashSeaViewPager.route) {
                            inclusive = true
                        }
                    }
                }
        )
    }
}

@Composable
fun Login(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var emailValue by remember {
        (mutableStateOf(""))
    }
    var passwordValue by remember {
        (mutableStateOf(""))
    }
    val passwordVisibility by remember {
        (mutableStateOf(false))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue), contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            LogoWithText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ExtraBigSpacing),
                color1 = LogoDarkBlue,
                color2 = LightBgShade
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 160.dp),
                painter = painterResource(id = R.drawable.ic_leaf),
                contentDescription = "Login"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .padding(top = 10.dp, start = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = Modifier
                    .padding(20.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Field(identity = "Email", emailValue, onValueChange = {
                    emailValue = it
                })
                Field(identity = "Password", passwordValue, onValueChange = {
                    passwordValue = it
                })
                Spacer(modifier = Modifier.padding(BigSpacing))
                CustomButton(text = "Sign In", navController =navController,mainViewModel)
                Spacer(modifier = Modifier.padding(28.dp))
                Text(
                    text = "Forgot Password?", style = Typography.h3,
                    modifier = Modifier.clickable(onClick = {})
                )
            }
            Spacer(modifier = Modifier.padding(28.dp))
        }

    }
}

@Composable
fun Signup(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var emailValue by remember {
        (mutableStateOf(""))
    }
    var passwordValue by remember {
        (mutableStateOf(""))
    }
    var nameValue by remember {
        (mutableStateOf(""))
    }
    var mobileValue by remember {
        (mutableStateOf(""))
    }
    val passwordVisibility by remember {
        (mutableStateOf(false))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue), contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            LogoWithText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ExtraBigSpacing),
                color1 = LogoDarkBlue,
                color2 = LightBgShade
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 60.dp, bottom = 5.dp),
                painter = painterResource(id = R.drawable.ic_pencil),
                contentDescription = "Signup"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .padding(top = 10.dp, start = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = Modifier
                    .padding(20.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Field(identity = "Name", nameValue, onValueChange = {
                    nameValue = it
                })
                Field(identity = "Mobile", mobileValue, onValueChange = {
                    mobileValue = it
                })
                Field(identity = "Email", emailValue, onValueChange = {
                    emailValue = it
                })
                Field(identity = "Password", passwordValue, onValueChange = {
                    passwordValue = it
                })
                Spacer(modifier = Modifier.padding(top = MediumSpacing))
                CustomButton(text = "Sign Up", navController = navController,mainViewModel)
                Spacer(modifier = Modifier.padding(top = BigSpacing))
                Text(
                    text = "By signing up, you agree to our",
                    fontSize = SmallTextSize,
                    color = TextLoginColor,
                    modifier = Modifier.clickable(onClick = {})
                )
                Text(
                    text = "Terms & Cookies Policy.",
                    style = Typography.h3,
                    color = TextLoginColor,
                    modifier = Modifier.clickable(onClick = {})
                )
            }
            Spacer(modifier = Modifier.padding(28.dp))
        }

    }
}

@Composable
fun Field(identity: String, content: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier,
            text = identity,
            color = TextLoginColor,
            fontSize = SmallTextSize,
        )
        Surface(
            shape = RoundedCornerShape(SmallSpacing),
            border = BorderStroke(
                width = MediumStroke,
                color = GreenBorder
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(6.5f)
        ) {
            TextField(
                value = content, onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    backgroundColor = Color.Transparent,
                    cursorColor = LightBg,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun CustomButton(text:String, navController: NavController,mainViewModel:MainViewModel) {
    Button(
        modifier = Modifier
            .fillMaxWidth(.45f)
            .height(50.dp)
            .padding(top = 2.dp),
        onClick = {
            mainViewModel.setSplashViewed()
            navController.navigate(Screen.BottomScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        },
        shape = RoundedCornerShape(ExtraBigSpacing),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LightBg,
            contentColor = MainBg
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = ExtraSmallSpacing
        ),
        contentPadding = PaddingValues(ExtraSmallSpacing)
    ){
        Text(
            text = text,
            style = Typography.h4,
            color = Color.White
        )
    }
}

