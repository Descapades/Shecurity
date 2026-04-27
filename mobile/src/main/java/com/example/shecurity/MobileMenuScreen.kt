package com.example.shecurity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shecurity.R
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable

@Composable
fun MobileMenuScreen(
    onContactsClick: () -> Unit,
    onMessageClick: () -> Unit,
    onContactViewClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 32.dp, top = 64.dp, end = 32.dp),
    ) {

        Text(
            text = "Menu",
            color = shecurity_pink,
            fontSize = 28.sp,
            fontFamily = ruluko_regular
        )

        Spacer(modifier = Modifier.height(42.dp))


        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = "Emergency Contacts",
                color = shecurity_purple,
                fontSize = 25.sp,
                fontFamily = ruluko_regular,
                modifier = Modifier.clickable {
                    onContactsClick()
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Emergency Message",
                color = shecurity_purple,
                fontSize = 25.sp,
                fontFamily = ruluko_regular,
                modifier = Modifier.clickable {
                    onMessageClick()
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "View Contact Screen",
                color = shecurity_purple,
                fontSize = 25.sp,
                fontFamily = ruluko_regular,
                modifier = Modifier.clickable {
                    onContactViewClick()
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(190.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = shecurity_pink
            )
        ) {
            Text(
                text = "Open On Watch",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = ruluko_regular
            )
        }

        Spacer(modifier = Modifier.height(44.dp))
    }
}