package com.example.shecurity

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

fun syncPrimaryContactToWatch(context: Context, primaryContactName: String, userName: String
) {
    val request = PutDataMapRequest.create("/shecurity_settings").apply {
        dataMap.putString("primary_contact", primaryContactName)
        dataMap.putString("user_name", userName)
        dataMap.putLong("updated_at", System.currentTimeMillis())
    }.asPutDataRequest()

    Wearable.getDataClient(context).putDataItem(request)
}