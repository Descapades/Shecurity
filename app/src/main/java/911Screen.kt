package com.example.shecurity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset

@Composable
fun DialingScreen(
    onCancelClick: () -> Unit,
    onFinish: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(5) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.offset(y = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dialing 911",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "in",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "00:0$timeLeft",
                color = shecurityPurple,
                fontSize = 18.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onCancelClick,
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = shecurityPink
                )
            ) {
                Text(
                    text = "Cancel",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = rulukoFont
                )
            }
        }
    }
}