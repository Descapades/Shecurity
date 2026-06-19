package com.example.shecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import android.content.Context
import androidx.compose.foundation.layout.Box
import android.util.Log

@Composable
fun ContactMapScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)

    val latitude = prefs.getFloat("last_alert_latitude", 0f).toDouble()
    val longitude = prefs.getFloat("last_alert_longitude", 0f).toDouble()
    val hasLocation = latitude != 0.0 && longitude != 0.0
    Log.d("ShecurityMap", "Map latitude = $latitude, longitude = $longitude, hasLocation = $hasLocation")

    val userName =
        prefs.getString("last_alert_user", "User") ?: "User"

    val sharedLocation = LatLng(latitude, longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sharedLocation, 15f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 40.dp, end = 24.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Viewing $userName's Location",
                color = shecurity_pink,
                fontSize = 24.sp,
                fontFamily = ruluko_regular
            )
        }

        if (hasLocation) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = sharedLocation),
                    title = "$userName's Location",
                    snippet = "Emergency location"
                )
            }
         }
         else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Location unavailable",
                    color = shecurity_pink,
                    fontSize = 24.sp,
                    fontFamily = ruluko_regular
                )
            }
        }
    }
}
