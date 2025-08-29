package com.example.app_jnproject.ui.screens.newscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


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
                Text("Erro: ${uiState.error}")
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
                                .padding(8.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                AsyncImage(
                                    model = "https://cdls.org.br/wp-content/uploads/cdlce_base/2017/06/3672591424_9591ce2a6c_o.jpg",
                                    contentDescription = "image_praça",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = doc.fields.title.stringValue)
                                Text(text = doc.fields.location.stringValue)
                                Text(text = doc.fields.date.stringValue)
                            }
                        }

                    }
                }
            }
        }
    }