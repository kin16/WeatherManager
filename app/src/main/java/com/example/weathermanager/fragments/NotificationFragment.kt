package com.example.weathermanager.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import com.example.weathermanager.TimeReceiver
import com.example.weathermanager.view.MainActivity
import kotlinx.android.synthetic.main.fragment_notification.*
import org.jetbrains.annotations.Nullable
import java.text.SimpleDateFormat
import java.util.*


class NotificationFragment : Fragment(),View.OnClickListener, TimePickerDialog.OnTimeSetListener  {
    private var mButton:Button? = null
    private var mCancel:Button? = null
    private var alarmManager:AlarmManager? = null
    private var text:TextView? = null
    private var box:CheckBox? = null
    private var set:TextView? = null
    private var date:TextView? = null
    private val TAG = "NotificationFragment"
    private lateinit var pref:SharedPreferences

    private var DIALOG_TIME = "Time"

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "OnCreateView")
        (activity as MainActivity).navigation.menu.getItem(2).isChecked = true

        val v = inflater.inflate(R.layout.fragment_notification, null)
        mButton = v.findViewById(R.id.button)
        mButton!!.setOnClickListener(this)
        mCancel = v.findViewById(R.id.button2)
        mCancel!!.setOnClickListener(this)
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        when(prefs.getString("theme", "Classic")){
            "Classic" -> {
                mButton?.background = activity!!.getDrawable(R.color.classicAccent)
                mCancel?.background = activity!!.getDrawable(R.color.classicAccent)
            }
            "Red" -> {
                mButton?.background = activity!!.getDrawable(R.color.redAccent)
                mCancel?.background = activity!!.getDrawable(R.color.redAccent)
            }
            "New" -> {
                mButton?.background = activity!!.getDrawable(R.color.newAccent)
                mCancel?.background = activity!!.getDrawable(R.color.newAccent)
            }
        }
        box = v.findViewById(R.id.box)

        text = v.findViewById(R.id.clock)
        val currentTime = Calendar.getInstance()

        text?.text = currentTime.get(Calendar.HOUR).toString() + ":" + currentTime.get(Calendar.MINUTE).toString()

        date = v.findViewById(R.id.date)
        date?.text = ""
        set = v.findViewById(R.id.set)
        set?.text = ""

        loadDate()

        return v
    }


    private fun saveDate(){
        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putString("time", text?.text.toString())
        ed.putString("date", date?.text.toString())
        ed.putString("set", set?.text.toString())
        ed.apply()
    }

    private fun loadDate(){
        pref = PreferenceManager.getDefaultSharedPreferences(activity)
        text?.text = pref.getString("time", "00:00")
        date?.text = pref.getString("date", "")
        set?.text = pref.getString("set", "")
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

                set?.text = ""
                date?.text = ""
                text?.text = "00:00"
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

        cal.timeInMillis = System.currentTimeMillis()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)


        Log.d(TAG, "${cal.time}, ${currentTime.time}")

        if(currentTime.time > cal.time){
            cal.set(Calendar.DATE, currentTime.get(Calendar.DATE) + 1)
        }
        Log.d(TAG, "${cal.time}, ${currentTime.time}")

        val bool = pref.getBoolean("hours", true)

        val text = clock
        if (bool){
            text.text = SimpleDateFormat("HH:mm").format(cal.time)
        }else{
            text.text = SimpleDateFormat("hh:mm").format(cal.time)
        }

        date?.text = SimpleDateFormat("dd, MMM").format(cal.time)
        set?.text = "Уведомление установлено:"

        saveDate()

        Toast.makeText(activity, "Time set: HOUR $hourOfDay, MINUTE $minute", Toast.LENGTH_LONG).show()
        Log.d(TAG, "Time set: HOUR $hourOfDay, MINUTE $minute")
        alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager?

        var interval = 12
        val period = pref.getString("list", "12")
        when (period){
            "3" -> interval = 3
            "6" -> interval = 6
            "24" -> interval = 24
        }

        if (box!!.isChecked) {
            alarmManager!!.setRepeating(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis, (1000 * 60 * 60 * interval).toLong(), pendingIntent)
            Log.d(TAG, "Repeating, ${1000 * 60 * 60 * interval}ms")
        }else{
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
            Log.d(TAG, "No repeat")
        }
    }

    override fun onPause() {
        saveDate()
        super.onPause()
    }
}