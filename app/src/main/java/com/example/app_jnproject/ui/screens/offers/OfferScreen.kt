package com.example.app_jnproject.ui.screens.offers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_jnproject.font.poppinsFamily

@Composable
fun OfferScreen(
    viewModel: OfferViewModel = viewModel()
) {
    val offersList by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Text(
            text = "Tudo em Oferta",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                letterSpacing = 1.sp,
                lineHeight = 40.sp
            ),
            color = Color(0xFF1C1C1E),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(offersList) { offer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(offer.img),
                            contentDescription = offer.titleOffer
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOfferScreen() {
    OfferScreen()
}