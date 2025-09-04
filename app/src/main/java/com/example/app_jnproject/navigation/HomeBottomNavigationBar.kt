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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.app_jnproject.R
import com.example.app_jnproject.ui.screens.home.HomeScreenLayout
import com.example.app_jnproject.ui.screens.newscreen.NewsScreenLayout

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
    NEWS(R.string.news, Icons.Default.Notifications, "news"),
    HOME(R.string.home, Icons.Default.Home, "home"),
    SEARCH(R.string.search, Icons.Default.Search, "search"),
    FAVORITES(R.string.favorites, Icons.Default.Star, "favorites")

}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = "news",
        modifier = modifier
    ) {
        composable(NavItems.NEWS.route) { NewsScreenLayout() }
        composable(NavItems.HOME.route) { HomeScreenLayout() }
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
                            .size(45.dp)
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
