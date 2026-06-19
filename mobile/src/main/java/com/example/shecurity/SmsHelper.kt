package com.example.shecurity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.core.content.ContextCompat

fun sendSmsMessage(
    context: Context,
    phoneNumber: String,
    message: String
): Boolean {
    val smsGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.SEND_SMS
    ) == PackageManager.PERMISSION_GRANTED

    if (!smsGranted || phoneNumber.isBlank()) {
        return false
    }

    val smsManager = context.getSystemService(SmsManager::class.java)
    smsManager.sendTextMessage(
        phoneNumber,
        null,
        message,
        null,
        null
    )

    return true
}