package com.example.app_jnproject.ui.screens.offers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OfferScreen() {
    val offers = List(50) { index -> "Oferta ${index + 1}" }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(offers) { offer ->
            OfferItem(offer)

        }
    }
}

@Composable
fun OfferItem(title: String) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = title)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOfferScreen() {
    OfferScreen()
}