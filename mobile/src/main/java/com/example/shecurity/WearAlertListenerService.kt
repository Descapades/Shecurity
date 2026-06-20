package com.example.shecurity

import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WearAlertListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/create_alert") {

            val payload = String(messageEvent.data)
            val parts = payload.split("|")

            if (parts.size >= 5) {
                val userName = parts[0]
                val contactName = parts[1]
                val message = parts[2]

                CoroutineScope(Dispatchers.IO).launch {
                    val mobileLocation = getMobileLocation(this@WearAlertListenerService)

                    val latitude = mobileLocation?.first ?: 0.0
                    val longitude = mobileLocation?.second ?: 0.0

                    val contacts = loadContacts(this@WearAlertListenerService)
                    val primaryContact = contacts.find { it.isPrimary }

                    val mapLink =
                        if (latitude != 0.0 && longitude != 0.0) {
                            "https://maps.google.com/?q=$latitude,$longitude"
                        } else {
                            "Location unavailable"
                        }

                    val smsMessage =
                        "$userName does not feel safe. Please follow their location: $mapLink"

                    if (primaryContact != null) {
                        sendSmsMessage(
                            context = this@WearAlertListenerService,
                            phoneNumber = primaryContact.phone,
                            message = smsMessage
                        )
                    }

                    val prefs = getSharedPreferences("shecurity_prefs", MODE_PRIVATE)

                    prefs.edit()
                        .putFloat("last_alert_latitude", latitude.toFloat())
                        .putFloat("last_alert_longitude", longitude.toFloat())
                        .putString("last_alert_user", userName)
                        .apply()

                    createEmergencyAlert(
                        userName = userName,
                        contactName = contactName,
                        message = message,
                        latitude = latitude,
                        longitude = longitude
                    )
                }
            }
        }

        if (messageEvent.path == "/safe_alert") {
            val userName = String(messageEvent.data)

            val contacts = loadContacts(this)
            val primaryContact = contacts.find { it.isPrimary }

            val safeMessage = "$userName has marked themselves as safe."

            if (primaryContact != null) {
                sendSmsMessage(
                    context = this,
                    phoneNumber = primaryContact.phone,
                    message = safeMessage
                )
            }
        }
    }
}