package com.example.app_jnproject.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.app_jnproject.R

class CuriosidadeWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        // Aqui você pode buscar dados do Firestore, API, etc.
        val titulo = CuriositiesRepositoryTitle.scheduledMessagesTitles.random()
        val mensagem = CuriositiesRepository.scheduledMessages.random()

        showNotification(titulo, mensagem)

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "curiosidades_channel"
        val manager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Curiosidades",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.caririfestlogo1)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
