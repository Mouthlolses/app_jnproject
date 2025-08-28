package com.example.app_jnproject.ui.screens.newscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
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