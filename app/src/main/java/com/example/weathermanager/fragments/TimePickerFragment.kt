package com.example.weathermanager.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import java.util.*


class TimePickerFragment : DialogFragment(){
    private val TAG = "TimePickerFragment"
    private var mListener: TimePickerDialog.OnTimeSetListener? = null

    fun setListener(mListener: TimePickerDialog.OnTimeSetListener) {
        Log.d(TAG, "Listener -> $mListener")

        this.mListener = mListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "OnCreateDialog")

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val bool = prefs.getBoolean("hours", true)

        return TimePickerDialog(activity, mListener,
            hour, minute, bool)
    }
}