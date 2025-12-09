package com.caririfest.admin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.caririfest.admin.ui.screens.validation_screens.producer_auth_account.AuthStateViewModel
import com.caririfest.admin.ui.screens.validation_screens.producer_auth_account.ProducerAuthViewModel


@Composable
fun RootNavigation() {

    val viewModel: AuthStateViewModel = hiltViewModel()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState(initial = false)

    val authViewModel: ProducerAuthViewModel = hiltViewModel()

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "app" else "auth"
    ) {

        composable("auth") {
            AuthNavigation(
                onAuthSuccess = {
                    navController.navigate("app") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("app") {
            AppNavigation(
                onLogout = {
                    authViewModel.logoutAdmin()
                    navController.navigate("auth") {
                        popUpTo("app") { inclusive = true }
                    }
                }
            )
        }
    }
}

