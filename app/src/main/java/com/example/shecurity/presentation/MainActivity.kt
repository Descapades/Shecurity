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
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.LaunchedEffect


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
                    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
                    val primaryContact =
                        prefs.getString("primary_contact", "Emergency Contact") ?: "Emergency Contact"

                    sendAlertToPhone(
                        userName = "Judy",
                        context = context,
                        contactName = primaryContact,
                        message = "SHEcurity alert sent to $primaryContact",
                        latitude = 0.0,
                        longitude = 0.0
                    )

                    currentScreen = "gps"
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

