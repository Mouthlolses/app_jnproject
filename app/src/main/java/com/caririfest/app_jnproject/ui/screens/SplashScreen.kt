package com.caririfest.app_jnproject.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caririfest.app_jnproject.R

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = { OvershootInterpolator(2f).getInterpolation(it) }
        )
    )

    val rotation by animateFloatAsState(
        targetValue = if (visible) 0f else -10f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)) +
                    scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(1000, easing = { OvershootInterpolator(2f).getInterpolation(it) })
                    ),
            exit = fadeOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.caririfestlogo1),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(500.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        rotationZ = rotation
                    }
            )
        }
    }
}