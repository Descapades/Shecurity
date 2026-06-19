package com.example.shecurity

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
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
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current

    var userName by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }

    var emergencyMessage by remember {
        mutableStateOf("I don't feel safe. Please follow my location.")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Shecurity",
            color = shecurity_pink,
            fontSize = 28.sp,
            fontFamily = ruluko_regular
        )

        Spacer(modifier = Modifier.height(20.dp))

        OnboardingTextField(
            value = userName,
            onValueChange = { userName = it },
            label = "Your Name"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OnboardingTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = "Primary Contact Name"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OnboardingTextField(
            value = contactPhone,
            onValueChange = { contactPhone = it },
            label = "Primary Contact Phone"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OnboardingTextField(
            value = emergencyMessage,
            onValueChange = { emergencyMessage = it },
            label = "Emergency Message"
        )

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = {
                if (
                    userName.isNotBlank() &&
                    contactName.isNotBlank() &&
                    contactPhone.isNotBlank()
                ) {
                    val primaryContact = Contact(
                        name = contactName,
                        phone = contactPhone,
                        isPrimary = true
                    )

                    saveContacts(context, listOf(primaryContact))

                    context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putString("user_name", userName)
                        .putString("primary_contact", contactName)
                        .putString("emergency_message", emergencyMessage)
                        .putBoolean("onboarding_complete", true)
                        .apply()

                    syncPrimaryContactToWatch(
                        context = context,
                        primaryContactName = contactName,
                        userName = userName
                    )

                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = shecurity_pink
            )
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = ruluko_regular
            )
        }
    }
}

@Composable
fun OnboardingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = shecurity_purple,
                fontFamily = ruluko_regular
            )
        },
        textStyle = LocalTextStyle.current.copy(
            color = shecurity_pink,
            fontSize = 16.sp,
            fontFamily = ruluko_regular
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

