package com.example.weathermanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.widget.Button
import com.example.weathermanager.R
import android.widget.Toast
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.PendingIntent
import android.content.Intent

import com.example.weathermanager.TimeReceiver
import java.util.*


class NotificationsFragment : Fragment(),View.OnClickListener  {
    private var mButton:Button? = null
    var DIALOG_TIME = "Time"
    lateinit var calendar: Calendar

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_notifications, null)
        mButton = v.findViewById(R.id.button)
        mButton!!.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View?) {
        val fragment = TimePickerFragment()
        fragment.show(fragmentManager!!, DIALOG_TIME)

        val alarmIntent = Intent(activity, TimeReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0)

        val alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager?
        calendar = fragment.cal
        alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, 1000 * 60 * 60 * 24, pendingIntent)
    }
}