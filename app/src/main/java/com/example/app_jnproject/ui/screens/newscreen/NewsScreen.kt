package com.example.app_jnproject.ui.screens.newscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.app_jnproject.ui.components.EventCard


@Composable
fun NewsScreenLayout() {
    Column() {
        LazyColumn {
            items(8) {
                EventCard()
            }
        }
    }
}