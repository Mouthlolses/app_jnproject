package com.example.app_jnproject.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
    ) {
        HomeScreenLayout()
    }
}


@Composable
fun HomeScreenLayout() {
    Text(
        text = "Olá Mundo!"
    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenLayout()
}