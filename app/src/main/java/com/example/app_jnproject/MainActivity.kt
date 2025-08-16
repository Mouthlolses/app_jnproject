package com.example.app_jnproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.app_jnproject.navigation.AppNavigation
import com.example.app_jnproject.ui.theme.App_jnprojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App_jnprojectTheme { AppJnToday() } }
    }
}


@Composable
fun AppJnToday() {
    AppNavigation()
}