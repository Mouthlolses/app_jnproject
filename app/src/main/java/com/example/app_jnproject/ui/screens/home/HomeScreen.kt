package com.example.app_jnproject.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.navigation.BottomNavigationBar
import com.example.app_jnproject.navigation.NavItems
import com.example.app_jnproject.navigation.NavigationGraph
import com.example.app_jnproject.ui.components.EventCard
import com.example.data.datasource.repository.EventsRepository


//Banner do Evento da Semana (no topo)
//
//Eventos em Alta (carrossel horizontal)
//
//Próximos Eventos (lista vertical com data)
//
//Curiosidades do Cariri (pequenos cards no final)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(repository: EventsRepository) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Defina as rotas que devem mostrar o BottomBar
    val bottomBarRoutes = listOf(
        NavItems.NEWS.route,
        NavItems.HOME.route,
        NavItems.SEARCH.route,
        NavItems.FAVORITES.route
    )


    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavigationGraph(
            repository = repository,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Text(
                text = "Bem Vindo",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold, // Negrito forte
                    fontSize = 32.sp, // Tamanho maior do que o padrão
                    letterSpacing = 1.sp // Espaçamento entre letras para um visual mais clean
                ),
                color = Color.Black
            )
        }
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
                    location = city.location,
                    date = city.date,
                    isFavorite = city.isFavorite,
                    onFavoriteClick = {},
                    onCardClick = {
                        navController.navigate("detailsScreen/${city.id}")
                    },
                    cardEnable = true
                )
            }
        }
    }
}
