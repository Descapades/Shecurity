package com.example.shecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddContactScreen(
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isPrimary by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 32.dp, top = 64.dp, end = 32.dp)
    ) {
        Text(
            text = "Emergency Contacts",
            color = shecurity_pink,
            fontSize = 28.sp,
            fontFamily = ruluko_regular
        )

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

        Text(
            text = "Email:",
            color = shecurity_purple,
            fontSize = 18.sp,
            fontFamily = ruluko_regular
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            textStyle = LocalTextStyle.current.copy(
                color = shecurity_pink,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            ),
            modifier = Modifier.fillMaxWidth()
        )


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
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val contacts = loadContacts(context)
                        val updatedContacts = contacts.toMutableList()

                        if (isPrimary) {
                            for (i in updatedContacts.indices) {
                                updatedContacts[i] = updatedContacts[i].copy(isPrimary = false)
                            }
                        }

                        updatedContacts.add(
                            Contact(
                                name = name,
                                phone = phone,
                                email = email,
                                isPrimary = isPrimary
                            )
                        )

                        saveContacts(context, updatedContacts)
                        if (isPrimary) {
                            syncPrimaryContactToWatch(context, name)
                        }

                        onSaveClick()
                    }
                },
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF999F)
                )
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

