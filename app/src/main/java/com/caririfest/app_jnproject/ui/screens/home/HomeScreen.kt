package com.caririfest.app_jnproject.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.caririfest.app_jnproject.navigation.BottomNavigationBar
import com.caririfest.app_jnproject.navigation.NavItems
import com.caririfest.app_jnproject.navigation.NavigationGraph
import com.caririfest.app_jnproject.ui.components.EventCard

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        NavItems.NEWS.route,
        NavItems.HOME.route,
        NavItems.SEARCH.route,
        NavItems.OFFER.route
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        NavigationGraph(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )

        if (currentRoute in bottomBarRoutes) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 32.dp)
                    .navigationBarsPadding()
            ) {
                BottomNavigationBar(navController)
            }
        }
    }
}


@Composable
fun HomeScreenLayout(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
    val cityLocation by viewModel.cityLocation.collectAsState()
    var query by remember { mutableStateOf("") }

    val filterCities = remember(query) {
        if (query.isEmpty()) {
            cityLocation
        } else {
            cityLocation.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Descubra",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            fontSize = 26.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(
            modifier = Modifier,
        ) {
            items(filterCities) { city ->
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
        Spacer(modifier = Modifier.padding(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                enabled = true,
                singleLine = true,
                placeholder = { Text(text = "Explore") },
                shape = RoundedCornerShape(26.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
                modifier = Modifier
                    .fillMaxWidth()
            )
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
