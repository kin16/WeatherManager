package com.example.weathermanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.weathermanager.model.NotificationService
import android.os.Build


class TimeReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm work", Toast.LENGTH_SHORT).show()
        Log.e("TimeReceiver", "Receive")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, NotificationService::class.java))
        } else {
            context!!.startService(Intent(context, NotificationService::class.java))
        }
    }
}
