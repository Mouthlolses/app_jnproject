package com.example.app_jnproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_jnproject.R

@Composable
fun OnBoardingButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(60.dp)
            .background(
                color = Color(0xFFE6AC00),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = Color.Black, // borda preta
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_action_arrow),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(28.dp)
        )
    }
}


@Preview
@Composable
fun OnBoardingButtonPreview() {
    OnBoardingButton(
        onClick = {},
        modifier = Modifier
    )
}