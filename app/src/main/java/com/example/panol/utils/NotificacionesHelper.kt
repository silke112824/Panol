package com.example.panol.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

fun mostrarNotificacionReserva(context: Context, sala: String) {
    val canalId = "canal_reservas_panol"
    val notificacionId = System.currentTimeMillis().toInt() // ID único para que no se sobreescriban
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Crear el canal de notificacion
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val canal = NotificationChannel(
            canalId,
            "Notificaciones de Pañol",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Canal para avisos de reserva de equipos"
        }
        notificationManager.createNotificationChannel(canal)
    }

    // hacer la notificacion :v
    val builder = NotificationCompat.Builder(context, canalId)
        .setSmallIcon(android.R.drawable.ic_menu_today) // Ícono nativo del sistema
        .setContentTitle("Reserva Confirmada")
        .setContentText("Tu solicitud para la $sala ha sido enviada al pañol.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    // tirar notificacion
    notificationManager.notify(notificacionId, builder.build())
}