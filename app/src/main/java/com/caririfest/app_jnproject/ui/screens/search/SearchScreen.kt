package com.caririfest.app_jnproject.ui.screens.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.caririfest.app_jnproject.connect.requestSingleLocationUpdate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen() {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<Point?>(null) }
    var searchQuery by remember { mutableStateOf("") }


    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

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
        MapboxMap(
            Modifier
                .fillMaxSize(),
            mapViewportState = rememberMapViewportState {
                setCameraOptions {
                    zoom(12.0)
                    center(userLocation ?: Point.fromLngLat(-39.3156, -7.2139))
                }
            }
        ) {
            MapEffect(userLocation) { mapView ->
                val fine =
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
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

                    mapView.viewport.transitionTo(
                        mapView.viewport.makeFollowPuckViewportState()
                    )
                }
            }
        }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar endereço ou local...") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp , start = 18.dp, end = 18.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                .align(Alignment.TopCenter)
        )
    }
}