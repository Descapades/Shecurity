package com.example.shecurity.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.clickable

@Composable
fun SafeScreen(
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
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You made it safe!",
                color = shecurityPink,
                fontSize = 18.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(0.dp))

            Image(
                painter = painterResource(id = R.drawable.safe_icon),
                contentDescription = "Safe Icon",
                modifier = Modifier.size(120.dp)
            )
        }
    }
}