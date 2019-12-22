package com.example.weathermanager.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.graphics.Color
import android.os.Build
import com.example.weathermanager.R
import com.example.weathermanager.view.MainActivity

class NotificationService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    override fun onCreate() {
        startNotification()
        super.onCreate()
    }

    private fun startNotification(){
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // I would suggest that you use IMPORTANCE_DEFAULT instead of IMPORTANCE_HIGH
            val channel =
                NotificationChannel("ID","NAME", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.lightColor = Color.BLUE
            channel.enableLights(true)

            channel.setShowBadge(true)
            notificationManager.createNotificationChannel(channel)
        }

        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText("Now the temperature is " + 9)
        bigText.setBigContentTitle("Temperature has dropped")
        bigText.setSummaryText("Temperature detail")

        val ii = Intent(this.getApplicationContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, ii, 0)


        val builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Title")
            .setContentText("Notification text")
            .setChannelId("ID")
            .setStyle(bigText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notification = builder.build()

        notificationManager.notify(1, notification)
        startForeground(1, notification)
    }
}