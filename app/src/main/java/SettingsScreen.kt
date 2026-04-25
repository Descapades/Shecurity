package com.example.shecurity.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
fun SettingsScreen(
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
            modifier = Modifier.offset(y = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Open Settings",
                color = shecurityPink,
                fontSize = 22.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "on Phone",
                color = shecurityPink,
                fontSize = 22.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.settings_icon),
                contentDescription = "Settings Icon",
                modifier = Modifier.size(58.dp)
            )
        }
    }
}