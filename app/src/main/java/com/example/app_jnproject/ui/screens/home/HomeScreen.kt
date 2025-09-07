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
import androidx.navigation.compose.rememberNavController
import com.example.app_jnproject.navigation.BottomNavigationBar
import com.example.app_jnproject.navigation.NavigationGraph
import com.example.app_jnproject.ui.components.EventCard


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
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
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            contentPadding = PaddingValues(),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Espaçamento entre os cards
        ) {
            items(cityLocation) { city ->
                EventCard(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp),
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
