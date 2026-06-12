package com.example.shecurity

import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class WearAlertListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/create_alert") {

            val payload = String(messageEvent.data)
            val parts = payload.split("|")

            if (parts.size >= 5) {
                val userName = parts[0]
                val contactName = parts[1]
                val message = parts[2]
                val latitude = parts[3].toDoubleOrNull() ?: 0.0
                val longitude = parts[4].toDoubleOrNull() ?: 0.0

                createEmergencyAlert(
                    userName = userName,
                    contactName = contactName,
                    message = message,
                    latitude = latitude,
                    longitude = longitude
                )
                showAlertNotification(
                    context = this,
                    title = "SHEcurity Alert",
                    message = "$userName requested location tracking."
                )
            }
        }
    }
}