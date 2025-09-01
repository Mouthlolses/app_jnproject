package com.example.app_jnproject.ui.screens.newscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.network.R


@Composable
fun NewsScreenLayout(
    viewModel: NewsViewModel = viewModel()
) {
    val uiState by viewModel.events.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchEvents()
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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
                        painter = painterResource(com.example.app_jnproject.R.drawable.mascot_sf),
                        contentDescription = "mascot"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        uiState.error!!,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.fetchEvents() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA500) // cor de destaque
                        )
                    ) {
                        Text(text = "Recarregar", color = Color.White)
                    }
                }
            }
        }

        else -> {
            LazyColumn {
                items(uiState.events) { doc ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF6E2B0)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = doc.fields.img.stringValue,
                                    contentDescription = "image_praca",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = doc.fields.title.stringValue)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = "Location",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = doc.fields.location.stringValue)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = doc.fields.date.stringValue)
                            }
                            IconButton(
                                onClick = { doc.fields.favorite.booleanValue },
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        color = Color(0xFFF5F5F5),
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = (if (doc.fields.favorite.booleanValue) Icons.Default.Star else Icons.Default.Star),
                                    contentDescription = "Favorite",
                                    tint = if (doc.fields.favorite.booleanValue) Color(0xFFFFA500) else Color.Gray,
                                    modifier = Modifier.size(22.dp)
                                )

                            }
                        }
                    }

                }
            }
        }
    }
}