package com.example.shecurity

import android.content.Intent
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.net.Uri

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
                    val prefs = getSharedPreferences("shecurity_prefs", MODE_PRIVATE)

                    val mobileLocation = getMobileLocation(this@WearAlertListenerService)

                    val savedLatitude = prefs.getFloat("last_alert_latitude", 0f).toDouble()
                    val savedLongitude = prefs.getFloat("last_alert_longitude", 0f).toDouble()

                    val latitude = mobileLocation?.first ?: savedLatitude
                    val longitude = mobileLocation?.second ?: savedLongitude

                    val hasLocation = latitude != 0.0 && longitude != 0.0

                    val contacts = loadContacts(this@WearAlertListenerService)
                    val primaryContact = contacts.find { it.isPrimary }

                    val mapLink =
                        if (hasLocation) {
                            "https://maps.google.com/?q=$latitude,$longitude"
                        } else {
                            ""
                        }

                    val smsMessage =
                        if (hasLocation) {
                            """
                            Shecurity Alert
        
                            $userName does not feel safe.
        
                            Please follow their location:
                            $mapLink
                            """.trimIndent()
                        } else {
                            """
                            Shecurity Alert
        
                            $userName does not feel safe.
        
                            Please contact them immediately.
                            """.trimIndent()
                        }

                    if (primaryContact != null) {
                        sendSmsMessage(
                            context = this@WearAlertListenerService,
                            phoneNumber = primaryContact.phone,
                            message = smsMessage
                        )
                    }

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

            val safeMessage =
                """
                Shecurity Update
    
                $userName has marked themselves as safe.
                """.trimIndent()

            if (primaryContact != null) {
                sendSmsMessage(
                    context = this,
                    phoneNumber = primaryContact.phone,
                    message = safeMessage
                )
            }
        }

        if (messageEvent.path == "/open_phone_settings") {
            val intent = Intent(
                this,
                MainActivity::class.java
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent)
        }

        if (messageEvent.path == "/call_emergency") {
            val intent = Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:8134587969")
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent)
        }
    }
}