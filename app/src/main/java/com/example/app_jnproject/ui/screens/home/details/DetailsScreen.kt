package com.example.app_jnproject.ui.screens.home.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_jnproject.ui.screens.home.HomeViewModel
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView

@Composable
fun DetailsScreen(
    cityId: Int,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val cityList by viewModel.cityLocation.collectAsState()
    val city = cityList.find { it.id == cityId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título do local
        Text(
            text = city?.name ?: "Local não encontrado",
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Inicializa o MapLibre ANTES de criar o MapView
        remember {
            MapLibre.getInstance(
                context,
                "",
                org.maplibre.android.WellKnownTileServer.MapLibre
            )
        }

        val mapView = remember { MapView(context) }

        DisposableEffect(Unit) {
            mapView.onStart()
            onDispose {
                mapView.onStop()
                mapView.onDestroy()
            }
        }

        // Exibe o mapa
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            factory = { mapView }
        ) { view ->
            view.getMapAsync { map ->
                map.setStyle("https://demotiles.maplibre.org/style.json")
                map.cameraPosition = CameraPosition.Builder()
                    .target(LatLng(0.0, 0.0)) // depois vamos colocar latitude real
                    .build()
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Exibe informações do local
        Text(text = "📍 ${city?.location ?: "Endereço indisponível"}")
        Text(text = "📅 ${city?.date ?: "Data não informada"}")
    }
}


