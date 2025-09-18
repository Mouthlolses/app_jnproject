package com.example.app_jnproject.ui.screens.offers

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_jnproject.font.interFamily
import com.example.app_jnproject.font.poppinsFamily
import com.example.app_jnproject.font.robotoFamily
import com.example.app_jnproject.ui.components.Tag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferScreen(
    viewModel: OfferViewModel = viewModel()
) {
    val offersList by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var selectedOffer by remember { mutableStateOf<OffersData?>(null) }

    // Estado do BottomSheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


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
            items(
                offersList,
                key = { offer -> offer.id.toLong() }
            ) { offer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            selectedOffer = offer
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(offer.img),
                            contentDescription = offer.titleOffer
                        )
                    }
                }
            }
        }
    }

    selectedOffer?.let { offer ->
        ModalBottomSheet(
            onDismissRequest = { selectedOffer = null },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = offer.titleOffer,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Cupons",
                    fontFamily = interFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.height(12.dp))
                if (offer.tags.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(offer.tags) { tag ->
                            Tag(
                                icon = offer.tagIcon,
                                text = tag,
                            )
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    if (offer.link.isNotEmpty()) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, offer.link.toUri())
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFF5733),
                                Color.White
                            )
                        ) {
                            Text(
                                text = "Acesse suas ofertas"
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }

                    if (offer.qrCode != null) {
                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "Acesse também via QRCode:",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(Modifier.height(16.dp))
                        Image(
                            painter = painterResource(offer.qrCode),
                            contentDescription = "qr_code",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(16.dp))
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