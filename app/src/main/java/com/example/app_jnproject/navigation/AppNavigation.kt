package com.example.app_jnproject.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.datastore.PreferencesKey
import com.example.app_jnproject.datastore.dataStore
import com.example.app_jnproject.ui.screens.home.HomeScreen
import com.example.app_jnproject.ui.screens.onboarding.OnBoardingScreen
import com.example.data.datasource.repository.EventsRepository
import kotlinx.coroutines.flow.map

@Composable
fun AppNavigation(repository: EventsRepository) {

    val navController = rememberNavController()
    val context = LocalContext.current

    val onBoardingShown by context.dataStore.data
        .map { prefs -> prefs[PreferencesKey.ONBOARDING_SHOWN] ?: false }
        .collectAsState(initial = null)

    when (onBoardingShown) {
        null -> {
            // Enquanto não sabe → mostra Splash/Loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        true -> {
            NavHost(
                navController = navController,
                startDestination = "homeScreen"
            ) {
                composable("onBoardingScreen") {
                    OnBoardingScreen(navController = navController)
                }
                composable("homeScreen") {
                    HomeScreen(repository = repository)
                }
            }
        }

        false -> {
            NavHost(
                navController = navController,
                startDestination = "onBoardingScreen"
            ) {
                composable("onBoardingScreen") {
                    OnBoardingScreen(navController = navController)
                }
                composable("homeScreen") {
                    HomeScreen(repository = repository)
                }
            }
        }

    }
}