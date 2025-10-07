package com.caririfest.app_jnproject.ui.screens.search

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import com.caririfest.app_jnproject.R
import com.caririfest.app_jnproject.connect.requestSingleLocationUpdate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
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

    fun getResizedBitmap(context: Context, resId: Int, width: Int, height: Int): Bitmap {
        val originalBitmap = BitmapFactory.decodeResource(context.resources, resId)
        return originalBitmap.scale(width, height, false)
    }

    val customMarkers = remember {
        mutableStateListOf(
            PointAnnotationOptions()
                .withPoint(Point.fromLngLat(-39.3156, -7.2139))
                .withIconImage(
                    getResizedBitmap(context, R.drawable.pointmap, 80, 80)
                ),
            PointAnnotationOptions()
                .withPoint(Point.fromLngLat(-39.3100, -7.2200))
                .withIconImage(
                    getResizedBitmap(context, R.drawable.pointmap, 80, 80)
                )
        )
    }

    val annotationConfig = AnnotationConfig(
        layerId = "custom-points-layer",   // camada usada para desenhar os pontos
        sourceId = "custom-points-source"  // fonte de dados do mapa
    )


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
            PointAnnotationGroup(
                annotations = customMarkers,
                annotationConfig = annotationConfig
            )
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
                .padding(top = 36.dp, start = 18.dp, end = 18.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                .align(Alignment.TopCenter)
        )
    }
}