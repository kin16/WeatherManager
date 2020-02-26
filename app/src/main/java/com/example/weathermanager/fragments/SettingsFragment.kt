package com.example.weathermanager.fragments

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.weathermanager.R

class SettingsFragment : PreferenceFragmentCompat() {
    private val TAG = "SettingsFragment"
    private var prefDark:Boolean? = null
    private var prefTheme:String? = null
    private var prefPeriod:String? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        Log.d(TAG, "onCreatePreferences")
    }

    private fun getPrefs(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        prefDark = prefs.getBoolean("prefCheck", false)
        prefTheme = prefs.getString("theme", "grey")
        prefPeriod = prefs.getString("list", "12")
        Log.d(TAG, "Getting preferences")
    }

    override fun onPause() {
        super.onPause()
        getPrefs()
        //Toast.makeText(context, "$prefDark , $prefTheme , $prefPeriod !", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "$prefDark , $prefTheme , $prefPeriod !")
    }
}