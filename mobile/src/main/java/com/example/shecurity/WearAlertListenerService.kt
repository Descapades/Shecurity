package com.example.shecurity

import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

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
                    Log.d("ShecurityLocation", "mobileLocation = $mobileLocation")

                    val latitude = mobileLocation?.first ?: 0.0
                    val longitude = mobileLocation?.second ?: 0.0
                    Log.d("ShecurityLocation", "Saved latitude = $latitude, longitude = $longitude")

                    val prefs = getSharedPreferences("shecurity_prefs", MODE_PRIVATE)

                    val saved = prefs.edit()
                        .putFloat("last_alert_latitude", latitude.toFloat())
                        .putFloat("last_alert_longitude", longitude.toFloat())
                        .putString("last_alert_user", userName)
                        .commit()

                    Log.d("ShecurityLocation", "Prefs saved = $saved")

                    createEmergencyAlert(
                        userName = userName,
                        contactName = contactName,
                        message = message,
                        latitude = latitude,
                        longitude = longitude
                    )
                    showAlertNotification(
                        context = this@WearAlertListenerService,
                        title = "SHEcurity Alert",
                        message = "$userName requested location tracking."
                    )
                }
            }
        }
    }
}