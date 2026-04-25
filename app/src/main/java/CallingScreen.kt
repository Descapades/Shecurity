package com.example.shecurity.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.shecurity.R

@Composable
fun CallingScreen(
    onEndCall: () -> Unit
) {
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
                text = "Calling...",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Text(
                text = "911",
                color = shecurityPink,
                fontSize = 20.sp,
                fontFamily = rulukoFont
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onEndCall,
                modifier = Modifier.size(70.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone_icon),
                    contentDescription = "End Call",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}