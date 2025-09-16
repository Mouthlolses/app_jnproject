package com.example.app_jnproject.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleCuriosidadeWorker(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<CuriosidadeWorker>(
        1, TimeUnit.HOURS // Ex.: a cada 6 horas
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "curiosidade_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}