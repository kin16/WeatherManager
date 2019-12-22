package com.example.weathermanager.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment : DialogFragment(){
    private var mListener: TimePickerDialog.OnTimeSetListener? = null

    fun setListener(mListener: TimePickerDialog.OnTimeSetListener) {
        this.mListener = mListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(
            activity, mListener, hour, minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}