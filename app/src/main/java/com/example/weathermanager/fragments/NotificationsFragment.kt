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
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.TimePicker

import com.example.weathermanager.TimeReceiver
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*


class NotificationsFragment : Fragment(),View.OnClickListener, TimePickerDialog.OnTimeSetListener  {
    private var mButton:Button? = null
    var DIALOG_TIME = "Time"

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
        fragment.setListener(this)
        fragment.show(fragmentManager!!, DIALOG_TIME)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val alarmIntent = Intent(activity, TimeReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0)

        val alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager?

        val cal = Calendar.getInstance()

        cal.setTimeInMillis(System.currentTimeMillis())
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        val text = clock
        text.text = cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + cal.get(Calendar.MINUTE).toString()

        Toast.makeText(activity, "Time set: HOUR $hourOfDay, MINUTE $minute", Toast.LENGTH_LONG).show()

        alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,
            cal.timeInMillis, 1000 * 60 * 60 * 24, pendingIntent)
    }
}