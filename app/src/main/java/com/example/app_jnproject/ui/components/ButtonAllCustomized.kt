package com.example.app_jnproject.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonAllCustomized(
    modifier: Modifier,
    colors: ButtonColors,
    elevation: ButtonElevation,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        elevation = elevation,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview
@Composable
fun ButtonAllCustomizedPreview() {
    ButtonAllCustomized(
        modifier = Modifier,
        onClick = {},
        text = "Texto Aqui",
        elevation = ButtonDefaults.buttonElevation(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9100),
            contentColor = Color.White
        ),
    )
}