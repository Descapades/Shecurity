package com.example.shecurity

import android.content.Context

data class Contact(
    val name: String,
    val phone: String,
    val isPrimary: Boolean
)

fun loadContacts(context: Context): MutableList<Contact> {
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
    val stored = prefs.getStringSet("contacts", emptySet()) ?: emptySet()

    return stored.map {
        val parts = it.split("|")
        Contact(
            name = parts[0],
            phone = parts.getOrElse(1) { "" },
            isPrimary = parts.getOrElse(2) { "false" }.toBoolean()
        )
    }.toMutableList()
}

fun saveContacts(context: Context, contacts: List<Contact>) {
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)

    val set = contacts.map {
        "${it.name}|${it.phone}|${it.isPrimary}"
    }.toSet()

    prefs.edit().putStringSet("contacts", set).apply()
}