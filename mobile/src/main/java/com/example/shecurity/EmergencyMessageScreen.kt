package com.example.shecurity

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmergencyMessageScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)

    var message by remember {
        mutableStateOf(
            prefs.getString(
                "emergency_message",
                "I don’t feel safe. Please follow my location."
            ) ?: ""
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 24.dp, top = 40.dp, end = 24.dp)
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
                text = "Emergency Message",
                color = shecurity_pink,
                fontSize = 24.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            textStyle = LocalTextStyle.current.copy(
                color = shecurity_pink,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    prefs.edit()
                        .putString("emergency_message", message)
                        .apply()

                    onBackClick()
                },
                modifier = Modifier
                    .width(110.dp)
                    .height(42.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = shecurity_pink
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