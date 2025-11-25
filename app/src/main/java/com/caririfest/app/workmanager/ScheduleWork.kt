package com.caririfest.app.workmanager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleCuriosidadeWorker(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<CuriosidadeWorker>(
        5, TimeUnit.HOURS
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "curiosidade_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}