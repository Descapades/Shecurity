package com.example.shecurity.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.shecurity.R

@Composable
fun GpsWeakScreen(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.offset(y = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GPS signal",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "is weak.",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(14.dp))

            Image(
                painter = painterResource(id = R.drawable.gps_weak_icon),
                contentDescription = "GPS signal weak",
                modifier = Modifier.size(96.dp)
            )
        }
    }
}
