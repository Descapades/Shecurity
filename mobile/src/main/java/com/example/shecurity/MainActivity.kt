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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { }

        setContent {
            var currentScreen by remember { mutableStateOf("menu") }
            var selectedContact by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }

            when (currentScreen) {
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
                    onSaveClick = { currentScreen = "contacts" }
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