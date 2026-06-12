package com.example.shecurity.presentation

import android.content.Context
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService

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

                if (!primaryContact.isNullOrBlank()) {
                    val prefs = getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)

                    prefs.edit()
                        .putString("primary_contact", primaryContact)
                        .apply()
                }
            }
        }
    }
}