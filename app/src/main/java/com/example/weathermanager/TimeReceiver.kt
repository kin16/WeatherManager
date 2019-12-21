package com.example.weathermanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.weathermanager.model.NotificationService

class TimeReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm work", Toast.LENGTH_SHORT).show()
        context!!.startService(Intent(context, NotificationService::class.java))
    }
}
