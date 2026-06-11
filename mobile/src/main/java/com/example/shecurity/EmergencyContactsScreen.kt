package com.example.shecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmergencyContactsScreen(
    onBackClick: () -> Unit,
    onAddContactClick: () -> Unit,
    onContactClick: (String) -> Unit
) {
    val context = LocalContext.current

    var contacts by remember {
        mutableStateOf(
            loadContacts(context).sortedByDescending { it.isPrimary }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 32.dp, top = 64.dp, end = 32.dp),
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

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {

            if (contacts.isEmpty()) {
                Text(
                    text = "No contacts added",
                    color = shecurity_purple,
                    fontSize = 20.sp,
                    fontFamily = ruluko_regular
                )
            } else {
                contacts.forEach { contact ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = contact.name,
                            color = shecurity_pink,
                            fontSize = 22.sp,
                            fontFamily = ruluko_regular,
                            modifier = Modifier.clickable {
                                onContactClick(contact.name)
                            }
                        )

                        if (contact.isPrimary) {
                            Text(
                                text = "Primary",
                                color = shecurity_purple,
                                fontSize = 18.sp,
                                fontFamily = ruluko_regular
                            )
                        }
                    }
                }
            }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick =
                            onAddContactClick,
                        modifier = Modifier
                            .width(150.dp)
                            .height(45.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = shecurity_pink
                        )
                    ) {
                        Text(
                            text = "Add Another",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = ruluko_regular
                        )
                    }
                }
            }
        }
    }




