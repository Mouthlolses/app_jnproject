package com.caririfest.app.workmanager

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun requestNotificationPermission(context: Context, activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(activity)
                .setTitle("Permissão de notificações")
                .setMessage("Precisamos da sua permissão para enviar alertas sobre novos eventos e curiosidades do Cariri Fest.")
                .setPositiveButton("Certo") { _, _ ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1001
                    )
                }
                .setNegativeButton("Não Quero", null)
                .show()
        }
    }
}
