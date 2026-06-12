/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.shecurity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.shecurity.presentation.theme.SHEcurityTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.shecurity.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.tasks.await
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.location.Priority
import kotlinx.coroutines.withTimeoutOrNull

val rulukoFont = FontFamily(
    Font(R.font.ruluko_regular)
)

val shecurityPink = Color(0xFFFF999F)
val shecurityPurple = Color(0xFF9A719D)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    var currentScreen by remember { mutableStateOf("main") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                val gpsAlertData = buildGpsAlertData(context)

                sendAlertToPhone(
                    userName = "Judy",
                    context = context,
                    contactName = gpsAlertData.primaryContact,
                    message = gpsAlertData.message,
                    latitude = gpsAlertData.latitude ?: 0.0,
                    longitude = gpsAlertData.longitude ?: 0.0
                )

                currentScreen =
                    if (gpsAlertData.latitude == null || gpsAlertData.longitude == null) {
                        "gpsWeak"
                    } else {
                        "gps"
                    }
            }
        }
    }

    LaunchedEffect(Unit) {
        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        val batteryPercent = if (level >= 0 && scale > 0) {
            level * 100 / scale
        } else {
            100
        }

        if (batteryPercent <= 15) {
            currentScreen = "battery"
        }
    }

    SHEcurityTheme {
        when (currentScreen) {

            "main" -> MainScreen(
                onGpsClick = {
                    val permissionGranted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (permissionGranted) {
                        scope.launch {
                            val gpsAlertData = buildGpsAlertData(context)

                            sendAlertToPhone(
                                userName = "Judy",
                                context = context,
                                contactName = gpsAlertData.primaryContact,
                                message = gpsAlertData.message,
                                latitude = gpsAlertData.latitude ?: 0.0,
                                longitude = gpsAlertData.longitude ?: 0.0
                            )

                            currentScreen =
                                if (gpsAlertData.latitude == null || gpsAlertData.longitude == null) {
                                    "gpsWeak"
                                } else {
                                    "gps"
                                }
                        }
                    } else {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                },
                on911Click = { currentScreen = "dialing" },
                onSwipeLeft = { currentScreen = "settings" }
            )

            "gps" -> GpsTrackingScreen(
                onSafeClick = { currentScreen = "safe" },
                on911Click = { currentScreen = "dialing" }
            )

            "safe" -> SafeScreen(
                onClick = { currentScreen = "main" }
            )

            "dialing" -> DialingScreen(
                onCancelClick = { currentScreen = "main" },
                onFinish = { currentScreen = "calling" }
            )

            "calling" -> CallingScreen(
                onEndCall = { currentScreen = "main" }
            )

            "settings" -> SettingsScreen(
                onClick = { currentScreen = "main" }
            )

            "battery" -> LowBatteryScreen(
                onClick = { currentScreen = "main" }
            )

            "gpsWeak" -> GpsWeakScreen(
                onClick = { currentScreen = "main" }
            )
        }
    }
}

@Composable
fun MainScreen(
    onGpsClick: () -> Unit,
    on911Click: () -> Unit,
    onSwipeLeft: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()

                    if (dragAmount < -20) {
                        onSwipeLeft()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are you safe?",
                color = shecurityPink,
                fontSize = 24.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    onClick = onGpsClick,
                    modifier = Modifier.size(58.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = shecurityPurple
                    )
                ) {
                    Text(
                        text = "GPS",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = rulukoFont
                    )
                }

                Button(
                    onClick = on911Click,
                    modifier = Modifier.size(58.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = shecurityPink
                    )
                ) {
                    Text(
                        text = "911",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = rulukoFont
                    )
                }
            }
        }
    }
}

data class GpsAlertData(
    val message: String,
    val primaryContact: String,
    val latitude: Double?,
    val longitude: Double?
)

suspend fun buildGpsAlertData(context: Context): GpsAlertData {
    val permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!permissionGranted) {
        val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
        val primaryContact = prefs.getString("primary_contact", "Emergency Contact") ?: "Emergency Contact"

        return GpsAlertData(
            message = "SHEcurity Alert sent to $primaryContact\nLocation unavailable",
            primaryContact = primaryContact,
            latitude = null,
            longitude = null
        )
    }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val location = try {
        withTimeoutOrNull(5000) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        }
    } catch (e: Exception) {
        null
    }

    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
    val primaryContact = prefs.getString("primary_contact", "Emergency Contact") ?: "Emergency Contact"

    return if (location != null) {
        GpsAlertData(
            message = "SHEcurity Alert sent to $primaryContact",
            primaryContact = primaryContact,
            latitude = location.latitude,
            longitude = location.longitude
        )
    } else {
        GpsAlertData(
            message = "SHEcurity Alert sent to $primaryContact\nLocation unavailable",
            primaryContact = primaryContact,
            latitude = null,
            longitude = null
        )
    }
}