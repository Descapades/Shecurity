package com.example.shecurity

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

fun syncPrimaryContactToWatch(context: Context, primaryContactName: String) {
    val request = PutDataMapRequest.create("/shecurity_settings").apply {
        dataMap.putString("primary_contact", primaryContactName)
        dataMap.putLong("updated_at", System.currentTimeMillis())
    }.asPutDataRequest()

    Wearable.getDataClient(context).putDataItem(request)
}