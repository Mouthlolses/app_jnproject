package com.example.app_jnproject.connect

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }


    when {
        locationPermissionState.allPermissionsGranted -> {
            Text("Permissão concedida ✅")
        }
        locationPermissionState.shouldShowRationale -> {
            Text("Precisamos da sua localização para mostrar o mapa corretamente.")
        }
        else -> {
            Text("Permissão negada ❌")
        }
    }
}

@SuppressLint("MissingPermission")
fun requestSingleLocationUpdate(context: Context, onLocation: (Location?) -> Unit) {
    val fused = LocationServices.getFusedLocationProviderClient(context)
    val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0)
        .setMaxUpdates(1)
        .build()

    fused.requestLocationUpdates(request, object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            fused.removeLocationUpdates(this)
            onLocation(result.lastLocation)
        }
    }, Looper.getMainLooper())
}