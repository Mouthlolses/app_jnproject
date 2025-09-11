package com.example.app_jnproject.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.app_jnproject.R
import com.example.app_jnproject.ui.screens.home.HomeScreenLayout
import com.example.app_jnproject.ui.screens.home.HomeViewModel
import com.example.app_jnproject.ui.screens.home.details.DetailsScreen
import com.example.app_jnproject.ui.screens.newscreen.NewsScreenLayout
import com.example.app_jnproject.ui.screens.newscreen.NewsViewModel
import com.example.app_jnproject.ui.screens.newscreen.details.NewsDetailsLayout
import com.example.app_jnproject.ui.screens.newscreen.details.NewsViewModelFactory
import com.example.data.datasource.repository.EventsRepository

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

enum class NavItems(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    NEWS(R.string.news, Icons.Default.Star, "news"),
    HOME(R.string.home, Icons.Default.Info, "home"),
    SEARCH(R.string.search, Icons.Default.Search, "search"),
    FAVORITES(R.string.favorites, Icons.Default.Favorite, "favorites")

}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier, repository: EventsRepository) {
    NavHost(
        navController = navController,
        startDestination = NavItems.NEWS.route,
        modifier = modifier
    ) {
        composable(NavItems.NEWS.route) { NewsScreenLayout(
            navController = navController,
            repository = repository
        ) }
        composable(NavItems.HOME.route) { HomeScreenLayout(navController = navController) }

        composable("detailsScreen/{cityId}") { backStackEntry ->
            val viewModel: HomeViewModel = viewModel()
            val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull()
            val city = viewModel.cityLocation.collectAsState().value.find { it.id == cityId }
            if (city != null) {
                DetailsScreen(city = city)
            }
        }

        composable(
            route = "newsDetailsScreen/{eventId}"
        ) { backStackEntry ->
            val viewModel: NewsViewModel = viewModel(
                factory = NewsViewModelFactory(repository)
            )
            val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
            val uiState = viewModel.events.collectAsState().value
            val event = uiState.events.find { it.id == eventId }

            LaunchedEffect(Unit) {
                viewModel.refreshEvents()
            }

            if (uiState.isLoading) {
                // Exibe um loading se ainda estiver carregando
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (event != null) {
                // Exibe os detalhes do evento
                NewsDetailsLayout(event = event)
            } else {
                // Exibe mensagem se não encontrar o evento
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Evento não encontrado!")
                }
            }

        }
        composable(NavItems.SEARCH.route) { }
        composable(NavItems.FAVORITES.route) { }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem(
            NavItems.NEWS.route,
            stringResource(NavItems.NEWS.title),
            NavItems.NEWS.icon
        ),
        BottomNavItem(
            NavItems.HOME.route,
            stringResource(NavItems.HOME.title),
            NavItems.HOME.icon
        ),
        BottomNavItem(
            NavItems.SEARCH.route,
            stringResource(NavItems.SEARCH.title),
            NavItems.SEARCH.icon
        ),
        BottomNavItem(
            NavItems.FAVORITES.route,
            stringResource(NavItems.FAVORITES.title),
            NavItems.FAVORITES.icon
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        tonalElevation = 8.dp,
        modifier = Modifier
            .height(125.dp),
        containerColor = Color.White
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            val backgroundColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent,
                animationSpec = tween(durationMillis = 300), label = ""
            )

            val contentColor by animateColorAsState(
                targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                animationSpec = tween(durationMillis = 300), label = ""
            )

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFFF6E2B0),
                                shape = MaterialTheme.shapes.large
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = Color(0xFFFF5733),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                label = {
                    AnimatedContent(
                        targetState = selected,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(200)) togetherWith
                                    fadeOut(animationSpec = tween(200))
                        }, label = ""
                    ) {
                        if (it) {
                            Text(item.label, color = contentColor)
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
