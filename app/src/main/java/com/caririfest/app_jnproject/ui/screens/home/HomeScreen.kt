package com.caririfest.app_jnproject.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.caririfest.app_jnproject.R
import com.caririfest.app_jnproject.font.poppinsFamily
import com.caririfest.app_jnproject.navigation.BottomNavigationBar
import com.caririfest.app_jnproject.navigation.NavItems
import com.caririfest.app_jnproject.navigation.NavigationGraph
import com.caririfest.app_jnproject.ui.components.CategoryCard
import com.caririfest.app_jnproject.ui.components.EventCard
import com.caririfest.app_jnproject.ui.components.Tag

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
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    recentViewModel: RecentEventViewModel = hiltViewModel()
) {
    val cityLocation by viewModel.cityLocation.collectAsState()
    val categoriesState by viewModel.categories.collectAsState()
    val recentEvents by recentViewModel.recentEvents.collectAsState()

    LaunchedEffect(Unit) {
        recentViewModel.loadEvents()
    }

    var query by remember { mutableStateOf("") }

//    val filterCities = remember(query) {
//        if (query.isEmpty()) {
//            cityLocation
//        } else {
//            cityLocation.filter { it.name.contains(query, ignoreCase = true) }
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item {
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
        }
        item {
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                modifier = Modifier,
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
        item {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "Explore as categorias",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 26.sp,
                color = Color.Black
            )
        }
        item {
            Spacer(modifier = Modifier.padding(4.dp))
            //categories
            LazyRow(
                modifier = Modifier
            ) {
                items(categoriesState) { value ->
                    CategoryCard(
                        icon = value.image,
                        title = value.nameCategories
                    )
                }
            }
        }

        item {
            //recentEvents
            if (recentEvents.isNotEmpty()) {
                Spacer(
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 18.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                )
                Text(
                    text = "Visto recentemente",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = 26.sp,
                    color = Color.Black
                )
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(recentEvents) { recentEvents ->
                    Card(
                        modifier = Modifier
                            .width(340.dp)
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                            .clickable(
                                onClick = {
                                    navController.navigate("newsDetailsScreen/${recentEvents.id}")
                                },
                                enabled = true,
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = recentEvents.img,
                                    contentDescription = stringResource(R.string.imageEvents),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = recentEvents.title,
                                        color = Color.Black,
                                        fontFamily = poppinsFamily,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = recentEvents.place,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = recentEvents.date,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Black
                                        )
                                    }
                                }
                                val isFavorite = recentEvents.favorite
                                Tag(
                                    text =
                                        if (isFavorite)
                                            stringResource(R.string.available)
                                        else
                                            stringResource(R.string.exhausted),
                                    backgroundColor = if (isFavorite) Color(0xFF4CAF50) else Color(
                                        0xFF9E9E9E
                                    )
                                )
                            }
                        }
                    }
                }
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
