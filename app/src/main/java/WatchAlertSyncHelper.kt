package com.example.shecurity.presentation

import android.content.Context
import com.google.android.gms.wearable.Wearable

fun sendAlertToPhone(
    context: Context,
    userName: String,
    contactName: String,
    message: String,
    latitude: Double,
    longitude: Double
) {
    val payload = "$userName|$contactName|$message|$latitude|$longitude"

    Wearable.getNodeClient(context).connectedNodes
        .addOnSuccessListener { nodes ->
            nodes.forEach { node ->
                Wearable.getMessageClient(context).sendMessage(
                    node.id,
                    "/create_alert",
                    payload.toByteArray()
                )
            }
        }
}

fun sendSafeToPhone(
    context: Context,
    userName: String
) {
    val payload = userName

    Wearable.getNodeClient(context).connectedNodes
        .addOnSuccessListener { nodes ->
            nodes.forEach { node ->
                Wearable.getMessageClient(context).sendMessage(
                    node.id,
                    "/safe_alert",
                    payload.toByteArray()
                )
            }
        }
}