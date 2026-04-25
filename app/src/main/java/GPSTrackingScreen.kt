package com.example.shecurity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun GpsTrackingScreen(
    onSafeClick: () -> Unit,
    on911Click: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mom",
                color = shecurityPink,
                fontSize = 24.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "is following",
                color = shecurityPink,
                fontSize = 24.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onSafeClick,
                    modifier = Modifier.size(58.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = shecurityPurple
                    )
                ) {
                    Text(
                        text = "Safe",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = rulukoFont
                    )
                }

                Button(
                    onClick = on911Click,
                    modifier = Modifier.size(58.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = shecurityPink
                    )
                ) {
                    Text(
                        text = "911",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = rulukoFont
                    )
                }
            }
        }
    }
}