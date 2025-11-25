package com.caririfest.app.ui.screens.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.caririfest.app.connect.requestSingleLocationUpdate
import com.caririfest.app.ui.components.SearchBarWithSuggestions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var userLocation by remember { mutableStateOf<Point?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val suggestions by viewModel.suggestions.collectAsState()
    val selectedPlace by viewModel.selectedPlace.collectAsState()

    // Permissões
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    // Quando a permissão é dada, obtemos a localização
    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            requestSingleLocationUpdate(context) { location ->
                location?.let {
                    userLocation = Point.fromLngLat(it.longitude, it.latitude)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // ───────────────────────── MAPA ─────────────────────────
        val mapViewportState = rememberMapViewportState {
            setCameraOptions {
                zoom(12.0)
                center(userLocation ?: Point.fromLngLat(-39.3156, -7.2139)) // default Crato 😄
            }
        }

        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
        ) {
            // Mostra o puck (local do usuário)
            MapEffect(userLocation) { mapView ->
                val fine =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                val coarse =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )

                if ((fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED)
                    && userLocation != null
                ) {
                    mapView.location.updateSettings {
                        locationPuck = createDefault2DPuck(withBearing = true)
                        enabled = true
                        pulsingEnabled = true
                    }

                    mapView.viewport.transitionTo(
                        mapView.viewport.makeFollowPuckViewportState()
                    )
                }
            }

            // Move para o resultado selecionado
            MapEffect(selectedPlace) { mapView ->
                selectedPlace?.coordinate?.let { coord ->
                    mapView.mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(Point.fromLngLat(coord.longitude(), coord.latitude()))
                            .zoom(14.0)
                            .build()
                    )
                }
            }
        }

        // ─────────────────────── Barra de Busca ───────────────────────
        SearchBarWithSuggestions(
            searchQuery = searchQuery,
            onQueryChange = {
                searchQuery = it
                if (it.length > 2 && userLocation != null) {
                    viewModel.search(it)
                }
            },
            suggestions = suggestions,
            onSuggestionClick = { item ->
                viewModel.selectSuggestion(item)
                searchQuery = item.name
            },
            modifier = Modifier
                .padding(top = 46.dp, start = 18.dp, end = 18.dp)
                .align(Alignment.TopCenter)
        )


    }
}