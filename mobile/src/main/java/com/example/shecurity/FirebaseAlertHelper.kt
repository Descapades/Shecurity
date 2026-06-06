package com.example.shecurity

import com.google.firebase.firestore.FirebaseFirestore

fun createEmergencyAlert(
    userName: String,
    contactName: String,
    message: String,
    latitude: Double,
    longitude: Double
) {
    val db = FirebaseFirestore.getInstance()

    val alert = hashMapOf(
        "userName" to userName,
        "contactName" to contactName,
        "message" to message,
        "latitude" to latitude,
        "longitude" to longitude,
        "status" to "active",
        "timestamp" to System.currentTimeMillis()
    )

    db.collection("alerts")
        .add(alert)
}
