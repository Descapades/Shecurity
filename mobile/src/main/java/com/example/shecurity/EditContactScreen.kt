package com.example.shecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.clickable
import android.content.Context

@Composable
fun EditContactScreen(
    contactName: String,
    onSaveClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    val savedContact = loadContacts(context).find { it.name == contactName }

    var name by remember { mutableStateOf(savedContact?.name ?: contactName) }
    var phone by remember { mutableStateOf(savedContact?.phone ?: "") }
    var isPrimary by remember { mutableStateOf(savedContact?.isPrimary ?: false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 32.dp, top = 64.dp, end = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Emergency Contacts",
                color = shecurity_pink,
                fontSize = 28.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Name:",
            color = shecurity_purple,
            fontSize = 18.sp,
            fontFamily = ruluko_regular
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            textStyle = LocalTextStyle.current.copy(
                color = shecurity_pink,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Phone Number:",
            color = shecurity_purple,
            fontSize = 18.sp,
            fontFamily = ruluko_regular
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            textStyle = LocalTextStyle.current.copy(
                color = shecurity_pink,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isPrimary,
                onCheckedChange = { isPrimary = it }
            )

            Text(
                text = "Primary",
                color = shecurity_purple,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val contacts = loadContacts(context)

                    val updatedContacts = contacts.filter { it.name != contactName }

                    saveContacts(context, updatedContacts)

                    onRemoveClick()
                }
            ) {
                Text(
                    text = "Remove",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = ruluko_regular
                )
            }

            Button(
                onClick = {
                    val contacts = loadContacts(context)

                    val updatedContacts = contacts.map {
                        if (it.name == contactName) {
                            Contact(name, phone, isPrimary)
                        } else {
                            if (isPrimary) it.copy(isPrimary = false) else it
                        }
                    }

                    saveContacts(context, updatedContacts)

                    val primaryContact = updatedContacts.find { it.isPrimary }
                    val prefs = context.getSharedPreferences(
                        "shecurity_prefs",
                        Context.MODE_PRIVATE
                    )

                    val userName =
                        prefs.getString("user_name", "User") ?: "User"
                    if (primaryContact != null) {
                        syncPrimaryContactToWatch(
                            context = context,
                            primaryContactName = primaryContact.name,
                            userName = userName)
                    }

                    onSaveClick()
                },
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = ruluko_regular
                )
            }
                }
    }
}


