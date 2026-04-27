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
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun GpsTrackingScreen(
    alertMessage: String,
    onSafeClick: () -> Unit,
    on911Click: () -> Unit
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("shecurity_prefs", Context.MODE_PRIVATE)
    val primaryContact = prefs.getString("primary_contact", "Mom") ?: "Mom"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CirculatingLightRing()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = primaryContact,
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

@Composable
fun CirculatingLightRing() {
    val infiniteTransition = rememberInfiniteTransition(label = "ringAnimation")

    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "ringRotation"
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val strokeWidth = 10.dp.toPx()
        val padding = 8.dp.toPx()

        drawArc(
            color = shecurityPurple,
            startAngle = rotation.value,
            sweepAngle = 70f,
            useCenter = false,
            topLeft = Offset(padding, padding),
            size = Size(
                size.width - padding * 2,
                size.height - padding * 2
            ),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
    }
}