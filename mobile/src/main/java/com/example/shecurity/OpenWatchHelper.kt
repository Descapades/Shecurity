package com.example.shecurity

import android.content.Context
import com.google.android.gms.wearable.Wearable

fun openWatchApp(context: Context) {
    Wearable.getNodeClient(context).connectedNodes
        .addOnSuccessListener { nodes ->
            nodes.forEach { node ->
                Wearable.getMessageClient(context).sendMessage(
                    node.id,
                    "/open_app",
                    ByteArray(0)
                )
            }
        }
}