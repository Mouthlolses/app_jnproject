package com.example.app_jnproject.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.R
import com.example.app_jnproject.ui.components.OnBoardingButton


@Composable
fun OnBoardingScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.White)
    ) {
        OnBoardingScreeLayout(
            onClick = { navController.navigate("homeScreen") }
        )
    }
}


@Composable
fun OnBoardingScreeLayout(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // deixa espaço pro botão
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.background_person),
                    contentDescription = null,
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(R.drawable.person_2),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 84.dp)
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
                    text = "Contra wireframe kit",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Wireframe is still important for ideation. " +
                            "It will help you to quickly test idea.",
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnBoardingButton(
                onClick = { onClick() },
                modifier = Modifier.size(60.dp)
            )
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