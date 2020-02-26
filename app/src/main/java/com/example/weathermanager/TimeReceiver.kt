package com.example.weathermanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.weathermanager.presenter.Presenter


class TimeReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("TimeReceiver", "Receive intent")

        Presenter.weather(context!!)
        setDate(context)
    }


    private fun setDate(context: Context?){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val ed: SharedPreferences.Editor = pref.edit()
        ed.putString("time", "00:00")
        ed.putString("date", "")
        ed.putString("set", "")
        ed.apply()
    }
}