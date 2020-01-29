package com.example.weathermanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.example.weathermanager.model.NotificationService
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.PreferenceManager
import com.example.weathermanager.view.MainActivity


class TimeReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        //Toast.makeText(context, "Alarm work", Toast.LENGTH_SHORT).show()
        Log.e("TimeReceiver", "Receive intent")
        Log.e("TimeReceiver", "New Notification showing")

        val notificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

        val ii = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, ii, 0)


        val builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Title")
            .setContentText("Notification text")
            .setChannelId("ID")
            .setStyle(bigText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notification = builder.build()

        setDate(context)

        notificationManager.notify(1, notification)
    }

    private fun setDate(context: Context?){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putString("time", "00:00")
        ed.putString("date", "")
        ed.putString("set", "")
        ed.commit()
    }
}
