package com.example.app_jnproject.ui.screens.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.app_jnproject.connect.RequestLocationPermission
import com.example.app_jnproject.connect.requestSingleLocationUpdate
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport

@Preview(showBackground = true)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {

    RequestLocationPermission()

    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<Point?>(null) }

    LaunchedEffect(Unit) {
        requestSingleLocationUpdate(context) { location ->
            location?.let {
                userLocation = Point.fromLngLat(it.longitude, it.latitude)
            }
        }
    }

    MapboxMap(
        Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        mapViewportState = rememberMapViewportState {
            setCameraOptions {
                zoom(12.0)
                center(userLocation ?: Point.fromLngLat(-39.3156, -7.2139))
            }
        }
    ) {
        // Habilita o ponto azul (se localização existir)
        MapEffect(userLocation) { mapView ->
            val fine =
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            val coarse = ContextCompat.checkSelfPermission(
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

                // faz a câmera seguir o puck
                mapView.viewport.transitionTo(
                    mapView.viewport.makeFollowPuckViewportState()
                )
            }
        }
    }
}