package com.example.shecurity.presentation

import android.content.Context
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import android.content.Intent
import com.google.android.gms.wearable.MessageEvent

class SettingsDataListenerService : WearableListenerService() {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            val dataItem = event.dataItem

            if (
                event.type == DataEvent.TYPE_CHANGED &&
                dataItem.uri.path == "/shecurity_settings"
            ) {
                val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                val primaryContact = dataMap.getString("primary_contact")
                val userName = dataMap.getString("user_name")

                if (!primaryContact.isNullOrBlank() || !userName.isNullOrBlank()) {

                    val prefs = getSharedPreferences(
                        "shecurity_prefs",
                        Context.MODE_PRIVATE
                    )

                    prefs.edit().apply {

                        if (!primaryContact.isNullOrBlank()) {
                            putString("primary_contact", primaryContact)
                        }

                        if (!userName.isNullOrBlank()) {
                            putString("user_name", userName)
                        }

                        apply()
                    }
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/open_app") {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            startActivity(intent)
        }
    }
}