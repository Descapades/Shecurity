package com.example.shecurity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

suspend fun getMobileLocation(context: Context): Pair<Double, Double>? {
    val hasFineLocation = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasFineLocation) {
        return null
    }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val currentLocation = withTimeoutOrNull(15000) {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).await()
    }

    val lastKnownLocation = fusedLocationClient.lastLocation.await()

    val finalLocation = currentLocation ?: lastKnownLocation

    return finalLocation?.let {
        Pair(it.latitude, it.longitude)
    }
}
