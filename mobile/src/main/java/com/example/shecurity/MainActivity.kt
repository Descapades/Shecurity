package com.example.shecurity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import android.os.Build
import kotlinx.coroutines.delay
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.content.Context

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { }

        val locationPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { }

        setContent {
            var currentScreen by remember {
                mutableStateOf("splash")
            }

            var selectedContact by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                delay(2000)

                val prefs = getSharedPreferences(
                    "shecurity_prefs",
                    Context.MODE_PRIVATE
                )

                val onboardingComplete =
                    prefs.getBoolean("onboarding_complete", false)

                currentScreen =
                    if (intent.getBooleanExtra("open_contact_alert", false)) {
                        "contactAlert"
                    } else if (!onboardingComplete) {
                        "onboarding"
                    } else {
                        "menu"
                    }
            }


            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }

                val fineLocationGranted = ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (!fineLocationGranted) {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            }

            when (currentScreen) {
                "splash" -> MobileSplashScreen()

                "onboarding" -> OnboardingScreen(
                    onFinish = {
                        currentScreen = "menu"
                    }
                )

                "menu" -> MobileMenuScreen(
                    onContactsClick = { currentScreen = "contacts" },
                    onMessageClick = { currentScreen = "message" },
                    onContactViewClick = { currentScreen = "contactAlert" }
                )

                "contacts" -> EmergencyContactsScreen(
                    onBackClick = { currentScreen = "menu" },
                    onAddContactClick = { currentScreen = "addContact" },
                    onContactClick = { contact ->
                        selectedContact = contact
                        currentScreen = "editContact"
                    }
                )

                "addContact" -> AddContactScreen(
                    onSaveClick = { currentScreen = "contacts" },
                    onBackClick = { currentScreen = "contacts" }
                )

                "editContact" -> EditContactScreen(
                    contactName = selectedContact,
                    onSaveClick = { currentScreen = "contacts" },
                    onRemoveClick = { currentScreen = "contacts" },
                    onBackClick = { currentScreen = "contacts" }
                )

                "message" -> EmergencyMessageScreen(
                    onBackClick = { currentScreen = "menu" }
                )

                "contactAlert" -> ContactAlertScreen(
                    onBackClick = { currentScreen = "menu" },
                    onFollowClick = { currentScreen = "contactMap" }
                )

                "contactMap" -> ContactMapScreen(
                    onBackClick = { currentScreen = "contactAlert" }
                )
            }
        }
    }
}