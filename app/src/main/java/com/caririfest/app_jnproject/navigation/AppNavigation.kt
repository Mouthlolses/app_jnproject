package com.caririfest.app_jnproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caririfest.app_jnproject.datastore.PreferencesKey
import com.caririfest.app_jnproject.datastore.dataStore
import com.caririfest.app_jnproject.ui.screens.SplashScreen
import com.caririfest.app_jnproject.ui.screens.home.HomeScreen
import com.caririfest.app_jnproject.ui.screens.onboarding.OnBoardingScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val onBoardingShownFlow = remember {
        context.dataStore.data
            .map { prefs -> prefs[PreferencesKey.ONBOARDING_SHOWN] ?: false }
    }

    val onBoardingShown by onBoardingShownFlow.collectAsState(initial = null)

    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(onBoardingShown) {
        if (onBoardingShown != null) {
            delay(2000)
            showSplash = false
        }
    }

    when {
        onBoardingShown == null || showSplash -> {
            SplashScreen()
        }

        else -> {
            val startDestination = if (onBoardingShown == true) {
                "homeScreen"
            } else {
                "onBoardingScreen"
            }

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable("onBoardingScreen") {
                    OnBoardingScreen(navController = navController)
                }
                composable("homeScreen") {
                    HomeScreen()
                }
            }
        }
    }
}