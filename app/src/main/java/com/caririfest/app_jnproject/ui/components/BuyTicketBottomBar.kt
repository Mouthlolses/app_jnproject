package com.caririfest.app_jnproject.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.caririfest.app_jnproject.R

@Composable
fun BuyTicketButtonBar(
    onClick: () -> Unit = { },
    enable: Boolean = true
) {
    Surface(
        tonalElevation = 26.dp,
        shadowElevation = 26.dp,
        color = Color.White,
        modifier = Modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = 0f + strokeWidth / 200
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .navigationBarsPadding()
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(36.dp),
            enabled = enable,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00C853),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.buyTicket),
                style = typography.bodyLarge,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
