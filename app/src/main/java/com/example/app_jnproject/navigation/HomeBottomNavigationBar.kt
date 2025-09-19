package com.example.app_jnproject.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.app_jnproject.R
import com.example.app_jnproject.font.poppinsFamily
import com.example.app_jnproject.ui.screens.home.HomeScreenLayout
import com.example.app_jnproject.ui.screens.home.HomeViewModel
import com.example.app_jnproject.ui.screens.home.details.DetailsScreen
import com.example.app_jnproject.ui.screens.news.NewsScreenLayout
import com.example.app_jnproject.ui.screens.news.NewsViewModel
import com.example.app_jnproject.ui.screens.news.details.NewsDetailsLayout
import com.example.app_jnproject.ui.screens.offers.OfferScreen
import com.example.app_jnproject.ui.screens.search.SearchScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Int
)

enum class NavItems(
    @param:StringRes val title: Int,
    val icon: Int,
    val route: String
) {
    NEWS(R.string.news, R.drawable.ic_action_news, "news"),
    HOME(R.string.home, R.drawable.ic_icon_regionalnews, "home"),
    SEARCH(R.string.search, R.drawable.ic_icon_mapsearch, "search"),
    OFFER(R.string.favorites, R.drawable.ic_action_sell, "offer")

}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavItems.NEWS.route,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ) {
        composable(NavItems.NEWS.route) {
            NewsScreenLayout(
                navController = navController
            )
        }
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
            val viewModel: NewsViewModel = hiltViewModel()

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
                NewsDetailsLayout(
                    event = event,
                    navController = navController
                )
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
        composable(NavItems.SEARCH.route) {
            SearchScreen()
        }
        composable(NavItems.OFFER.route) {
            OfferScreen()
        }
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
            NavItems.OFFER.route,
            stringResource(NavItems.OFFER.title),
            NavItems.OFFER.icon
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.drawBehind {
            val strokeWidth = 1.dp.toPx()
            val y = 0f + strokeWidth / 200
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth
            )
        },
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

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
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (selected) Color(0xFFFF6D00) else Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
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
                            Text(
                                item.label,
                                color = if (selected) Color(0xFFFF6D00) else Color.Gray,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
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
