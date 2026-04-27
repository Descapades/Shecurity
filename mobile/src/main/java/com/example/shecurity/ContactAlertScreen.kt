package com.example.shecurity

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactAlertScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)

    val alertMessage = prefs.getString(
        "emergency_message",
        "I don't feel safe. Please follow my location."
    ) ?: "I don't feel safe. Please follow my location."

    var replyMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 24.dp, top = 40.dp, end = 24.dp, bottom = 24.dp)
    ) {
        Row(
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

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(shecurity_purple, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "J",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = ruluko_regular
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Judy",
                color = shecurity_pink,
                fontSize = 36.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2B2B2B), RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(
                text = alertMessage,
                color = shecurity_pink,
                fontSize = 24.sp,
                fontFamily = ruluko_regular
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Click to follow",
                color = shecurity_purple,
                fontSize = 24.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = replyMessage,
            onValueChange = { replyMessage = it },
            placeholder = {
                Text(
                    text = "Type message",
                    color = shecurity_pink,
                    fontSize = 24.sp,
                    fontFamily = ruluko_regular
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2B2B2B),
                unfocusedContainerColor = Color(0xFF2B2B2B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

