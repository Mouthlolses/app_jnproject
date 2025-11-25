package com.caririfest.app.connect

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


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