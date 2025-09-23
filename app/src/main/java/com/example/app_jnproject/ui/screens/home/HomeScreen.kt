package com.example.app_jnproject.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.navigation.BottomNavigationBar
import com.example.app_jnproject.navigation.NavItems
import com.example.app_jnproject.navigation.NavigationGraph
import com.example.app_jnproject.ui.components.BuyTicketButtonBar
import com.example.app_jnproject.ui.components.EventCard


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Defina as rotas que devem mostrar o BottomBar
    val bottomBarRoutes = listOf(
        NavItems.NEWS.route,
        NavItems.HOME.route,
        NavItems.SEARCH.route,
        NavItems.OFFER.route
    )

    Scaffold(
        bottomBar = {
            when (currentRoute) {
                in bottomBarRoutes -> {
                    BottomNavigationBar(navController)
                }

                "newsDetailsScreen/{eventId}" -> {
                    BuyTicketButtonBar(
                        onClick = { }
                    )
                }
            }

        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}


@Composable
fun HomeScreenLayout(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {

    val cityLocation by viewModel.cityLocation.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.padding(12.dp))
        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(cityLocation) { city ->
                EventCard(
                    modifier = Modifier,
                    img = city.img,
                    title = city.name,
                    onCardClick = {
                        navController.navigate("detailsScreen/${city.id}")
                    },
                    cardEnable = false
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLayoutPreview() {
    HomeScreenLayout(
        navController = rememberNavController()
    )
}
