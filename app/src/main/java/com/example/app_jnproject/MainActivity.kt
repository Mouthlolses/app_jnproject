package com.example.app_jnproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationManagerCompat
import com.example.app_jnproject.navigation.AppNavigation
import com.example.app_jnproject.ui.theme.App_jnprojectTheme
import com.example.app_jnproject.workmanager.NOTIFICATION_ID
import com.example.app_jnproject.workmanager.requestNotificationPermission
import com.example.app_jnproject.workmanager.scheduleCuriosidadeWorker
import com.example.data.datasource.database.AppDatabase
import com.example.data.datasource.repository.EventsRepository
import com.example.network.data.EventsApi


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(context = this)
        val repository = EventsRepository(
            api = EventsApi.retrofitService,
            eventDao = db.eventDao()
        )
        requestNotificationPermission(this, this)
        scheduleCuriosidadeWorker(this)
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)

        setContent { App_jnprojectTheme { AppJnToday(repository = repository) } }
    }

}

@Composable
fun AppJnToday(repository: EventsRepository) {
    AppNavigation(repository = repository)
}