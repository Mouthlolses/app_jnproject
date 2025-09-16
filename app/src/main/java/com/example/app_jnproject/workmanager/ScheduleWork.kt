package com.example.app_jnproject.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

fun scheduleCuriosidadeWorker(context: Context) {
    val currentTime = LocalTime.now()
    val targetTime = LocalTime.of(9, 0)
    val delay = Duration.between(currentTime, targetTime)
        .toMillis().let { if (it < 0) it + TimeUnit.DAYS.toMillis(1) else it }

    val workRequest = PeriodicWorkRequestBuilder<CuriosidadeWorker>(
        3, TimeUnit.HOURS // Ex.: a cada 6 horas
    ).setInitialDelay(delay, TimeUnit.MILLISECONDS).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "curiosidade_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}