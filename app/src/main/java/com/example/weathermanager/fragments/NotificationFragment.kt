package com.example.weathermanager.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import com.example.weathermanager.TimeReceiver
import kotlinx.android.synthetic.main.fragment_notification.*
import org.jetbrains.annotations.Nullable
import java.util.*


class NotificationFragment : Fragment(),View.OnClickListener, TimePickerDialog.OnTimeSetListener  {
    private var mButton:Button? = null
    private var mCancel:Button? = null
    private var alarmManager:AlarmManager? = null
    private var text:TextView? = null
    private var box:CheckBox? = null
    private val TAG = "NotificationFragment"

    var DIALOG_TIME = "Time"

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "OnCreateView")

        val v = inflater.inflate(R.layout.fragment_notification, null)
        mButton = v.findViewById(R.id.button)
        mButton!!.setOnClickListener(this)
        mCancel = v.findViewById(R.id.button2)
        mCancel!!.setOnClickListener(this)
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val prefTheme = prefs.getString("theme", "Green")
        when(prefTheme){
            "Green" -> {
                mButton?.background = activity!!.getDrawable(R.color.greenPrimary)
                mCancel?.background = activity!!.getDrawable(R.color.greenPrimary)
            }
            "Red" -> {
                mButton?.background = activity!!.getDrawable(R.color.redPrimary)
                mCancel?.background = activity!!.getDrawable(R.color.redPrimary)
            }
            "Blue" -> {
                mButton?.background = activity!!.getDrawable(R.color.bluePrimary)
                mCancel?.background = activity!!.getDrawable(R.color.bluePrimary)
            }
            "Grey" -> {
                mButton?.background = activity!!.getDrawable(R.color.greyAccent)
                mCancel?.background = activity!!.getDrawable(R.color.greyAccent)
            }
        }
        box = v.findViewById(R.id.box)

        text = v.findViewById(R.id.clock)
        val currentTime = Calendar.getInstance()

        text?.text = currentTime.get(Calendar.HOUR).toString() + ":" + currentTime.get(Calendar.MINUTE).toString()
        return v
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.button -> {
                val fragment = TimePickerFragment()
                fragment.setListener(this)
                fragment.show(fragmentManager!!, DIALOG_TIME)
            }
            R.id.button2 -> {
                val alarmIntent = Intent(activity, TimeReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0)

                alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager?

                Log.d("TimeReceiver", "Canceled")
                alarmManager!!.cancel(pendingIntent)
            }
        }
        Log.d(TAG, "OnClick -> ${v.id}")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "OnTimeSet")

        val alarmIntent = Intent(activity, TimeReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0)

        val currentTime = Calendar.getInstance()
        val cal = Calendar.getInstance()

        cal.setTimeInMillis(System.currentTimeMillis())
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)


        Log.d(TAG, "${cal.time}, ${currentTime.time}")

        if(currentTime.time > cal.time){
            cal.set(Calendar.DATE, currentTime.get(Calendar.DATE) + 1)
        }
        Log.d(TAG, "${cal.time}, ${currentTime.time}")

        val text = clock
        text.text = cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + cal.get(Calendar.MINUTE).toString()

        Toast.makeText(activity, "Time set: HOUR $hourOfDay, MINUTE $minute", Toast.LENGTH_LONG).show()
        Log.d(TAG, "Time set: HOUR $hourOfDay, MINUTE $minute")
        alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager?

        if (box!!.isChecked) {
            alarmManager!!.setRepeating(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis, 1000 * 60 * 60 * 24, pendingIntent)
            Log.d(TAG, "Repeating, ${1000 * 60 * 60 * 24}ms")
        }else{
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
            Log.d(TAG, "No repeat")
        }
    }
}