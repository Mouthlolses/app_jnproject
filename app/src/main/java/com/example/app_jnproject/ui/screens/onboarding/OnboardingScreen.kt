package com.example.app_jnproject.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.R
import com.example.app_jnproject.ui.components.ButtonAllCustomized
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavHostController
) {

    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            delay(4000)

            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            scope.launch {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { pager ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (pager) {
                    0 -> OnBoardingScreeLayout(
                        image = R.drawable.background_person,
                        image2 = R.drawable.person_2,
                        title = R.string.title,
                        subTitle = R.string.subTitle
                    )

                    1 -> OnBoardingScreeLayout(
                        image = R.drawable.background_yellow,
                        image2 = R.drawable.person_3,
                        title = R.string.title,
                        subTitle = R.string.subTitle
                    )

                    2 -> OnBoardingScreeLayout(
                        image = R.drawable.background_claro,
                        image2 = R.drawable.person_11,
                        title = R.string.title,
                        subTitle = R.string.subTitle
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 110.dp)
        ) {
            repeat(3) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 12.dp else 8.dp)
                        .background(
                            if (isSelected) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
        ) {
            ButtonAllCustomized(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 14.dp, end = 14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                onClick = { navController.navigate("homeScreen") },
                text = stringResource(R.string.start)
            )
        }
    }
}


@Composable
fun OnBoardingScreeLayout(
    @DrawableRes image: Int,
    @DrawableRes image2: Int,
    @StringRes title: Int,
    @StringRes subTitle: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = stringResource(R.string.backgroundImageOnbDescription),
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(image2),
                    contentDescription = stringResource(R.string.imageOnbDescription),
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .width(200.dp)
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(title),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(subTitle),
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(
    name = "api31",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 31
)
@Preview(
    name = "api32",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 32
)
@Preview(
    name = "api33",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 33
)
@Preview(
    name = "api34",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 34
)
@Preview(
    name = "api35",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 35
)
@Preview(
    name = "api36",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 36
)
@Preview(
    name = "pixel_5",
    device = "id:pixel_5"
)
@Preview(
    name = "Tablet",
    device = Devices.TABLET
)
@Composable
fun OnBoardingScreeLayoutPreview() {
    val navController = rememberNavController()
    OnBoardingScreen(
        navController = navController
    )
}