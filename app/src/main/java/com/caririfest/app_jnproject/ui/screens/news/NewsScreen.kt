package com.caririfest.app_jnproject.ui.screens.news

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.caririfest.app_jnproject.R
import com.caririfest.app_jnproject.font.poppinsFamily
import com.caririfest.app_jnproject.font.robotoFamily
import com.caririfest.app_jnproject.ui.components.Tag
import com.caririfest.app_jnproject.ui.screens.home.RecentEventViewModel
import com.caririfest.data.datasource.model.EventEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenLayout(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    recentEventViewModel: RecentEventViewModel = hiltViewModel()
) {
    val uiState by viewModel.events.collectAsState()
    val uiStateHotFilter = uiState.events.filter { it.fields.hot.booleanValue }
    val pagerState = rememberPagerState(pageCount = { uiStateHotFilter.size })
    val scope = rememberCoroutineScope()


    LaunchedEffect(
        pagerState.isScrollInProgress
    ) {
        if (!pagerState.isScrollInProgress && pagerState.pageCount > 0) {
            delay(6000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            scope.launch {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(R.color.principal_color)
                )
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.mascot_sf),
                        contentDescription = stringResource(R.string.mascot)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        uiState.error!!,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "O que vai ser hoje?",
                                fontFamily = poppinsFamily
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                            navigationIconContentColor = Color.Transparent,
                            titleContentColor = Color.DarkGray,
                            actionIconContentColor = Color.Transparent,
                            subtitleContentColor = Color.Black,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFF9800),
                                        Color(0xFFFFEA00)
                                    )
                                )
                            )
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEFEFEF))
                        .padding(innerPadding)
                        .navigationBarsPadding(),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Imperdíveis",
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color(0xFF565555),
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 12.dp, bottom = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(16.dp),
                                    clip = false
                                )
                                .background(Color.White)
                        ) {
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(366.dp)
                            ) { page ->
                                val event =
                                    uiStateHotFilter[page]
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = {
                                                recentEventViewModel.onEventOpened(
                                                    EventEntity(
                                                        id = event.id,
                                                        title = event.fields.title.stringValue,
                                                        desc = event.fields.desc.stringValue,
                                                        date = event.fields.date.stringValue,
                                                        img = event.fields.img.stringValue,
                                                        link = event.fields.link.stringValue,
                                                        location = event.fields.location.stringValue,
                                                        place = event.fields.place.stringValue,
                                                        time = event.fields.time.stringValue,
                                                        favorite = event.fields.favorite.booleanValue,
                                                        hot = event.fields.hot.booleanValue
                                                    )
                                                )
                                                navController.navigate("newsDetailsScreen/${event.id}")
                                            },
                                            enabled = true
                                        )
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 16.dp,
                                            bottom = 16.dp
                                        ),
                                ) {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(bottom = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        AsyncImage(
                                            model = event.fields.img.stringValue,
                                            contentDescription = stringResource(R.string.imageEvents),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(RoundedCornerShape(16.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = event.fields.title.stringValue,
                                            fontFamily = robotoFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = Color(0xFF333333),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp)
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.DateRange,
                                                contentDescription = stringResource(R.string.date),
                                                tint = Color.DarkGray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = event.fields.date.stringValue,
                                                fontFamily = robotoFamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = Color(0xFF666666),
                                                textAlign = TextAlign.Center,
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Place,
                                                contentDescription = stringResource(R.string.date),
                                                tint = Color.Red,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = event.fields.location.stringValue,
                                                fontFamily = robotoFamily,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 14.sp,
                                                color = Color(0xFF888888),
                                                textAlign = TextAlign.Center,
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                repeat(uiStateHotFilter.size) { index ->
                                    val isSelected = pagerState.currentPage == index
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(if (isSelected) 12.dp else 8.dp)
                                            .background(
                                                if (isSelected) Color(0xFFFF6D00) else Color.LightGray,
                                                shape = CircleShape
                                            )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(26.dp))
                        }
                    }
                    items(uiState.events) { doc ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Em Alta",
                                fontFamily = robotoFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF565555),
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 16.dp)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 12.dp,
                                    bottom = 16.dp
                                )
                                .clickable(
                                    onClick = {
                                        recentEventViewModel.onEventOpened(
                                            EventEntity(
                                                id = doc.id,
                                                title = doc.fields.title.stringValue,
                                                desc = doc.fields.desc.stringValue,
                                                date = doc.fields.date.stringValue,
                                                img = doc.fields.img.stringValue,
                                                link = doc.fields.link.stringValue,
                                                location = doc.fields.location.stringValue,
                                                place = doc.fields.place.stringValue,
                                                time = doc.fields.time.stringValue,
                                                favorite = doc.fields.favorite.booleanValue,
                                                hot = doc.fields.hot.booleanValue
                                            )
                                        )
                                        navController.navigate("newsDetailsScreen/${doc.id}")
                                    },
                                    enabled = true,
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
                                        model = doc.fields.img.stringValue,
                                        contentDescription = stringResource(R.string.imageEvents),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(16.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
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
                                            text = doc.fields.title.stringValue,
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )

                                        Spacer(modifier = Modifier.height(14.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.DateRange,
                                                contentDescription = stringResource(R.string.date),
                                                tint = Color.DarkGray,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = doc.fields.date.stringValue,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Place,
                                                contentDescription = stringResource(R.string.location),
                                                tint = Color.Red,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Text(
                                                text = doc.fields.location.stringValue,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                    val isFavorite = doc.fields.favorite.booleanValue
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
}