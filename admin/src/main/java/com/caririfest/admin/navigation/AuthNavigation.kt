package com.caririfest.admin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caririfest.admin.ui.screens.validation_screens.producer_auth_account.ProducerAuthScreen
import com.caririfest.admin.ui.screens.validation_screens.producer_create_account.ProducerCreateAccountScreen

@Composable
fun AuthNavigation(onAuthSuccess: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            ProducerAuthScreen(
                navController = navController
            )
        }

        composable("register") {
            ProducerCreateAccountScreen()
        }
    }
}