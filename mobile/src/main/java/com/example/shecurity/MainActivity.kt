package com.example.shecurity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentScreen by remember { mutableStateOf("menu") }
            var selectedContact by remember { mutableStateOf("") }

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
                    onBackClick = { currentScreen = "menu" }
                )


            }
        }
    }
}