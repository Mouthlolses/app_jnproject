package com.example.app_jnproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.ui.screens.home.HomeScreen
import com.example.app_jnproject.ui.screens.onboarding.OnBoardingScreen
import com.example.data.datasource.repository.EventsRepository

@Composable
fun AppNavigation(repository: EventsRepository) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onBoardingScreen"
    ) {
        composable(
            "onBoardingScreen"
        ) {
            OnBoardingScreen(
                navController = navController
            )
        }
        composable(
            "homeScreen"
        ) {
            HomeScreen(repository = repository)
        }
    }
}