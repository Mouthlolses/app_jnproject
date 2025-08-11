package com.example.app_jnproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_jnproject.ui.screens.home.HomeScreen
import com.example.app_jnproject.ui.theme.App_jnprojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App_jnprojectTheme { HomeScreen() } }
    }
}