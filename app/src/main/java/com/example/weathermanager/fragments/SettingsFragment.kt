package com.example.weathermanager.fragments

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import androidx.preference.Preference as Preference

class SettingsFragment : PreferenceFragmentCompat() {
    private val TAG = "SettingsFragment"
    private var prefDark:String? = null
    private var prefTheme:String? = null
    private var prefPeriod:String? = null
    private var prefKey:String? = null
    private var prefHours:Boolean? = null
    private var prefFormat:String? = null
    private var prefYourFormat:String? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        val preference = findPreference("theme")
        preference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                activity!!.recreate()
                true
            }

        val preference2 = findPreference("prefDark")
        preference2.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                activity!!.recreate()
                true
            }

        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val format = prefs.getString("format", "HH:mm dd/mm/yyyy")!!
        if (format != "your"){
            preferenceScreen.findPreference("yourFormat").isEnabled = false
        }
        val preference3 = findPreference("format")
        preference3.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, value ->
                preferenceScreen.findPreference("yourFormat").isEnabled = value == "your"
                true
            }

        Log.d(TAG, "onCreatePreferences")
    }

    private fun getPrefs(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        prefDark = prefs.getString("prefDark", "auto")
        prefTheme = prefs.getString("theme", "Classic")
        prefPeriod = prefs.getString("list", "12")
        prefKey = prefs.getString("key", null)
        prefHours = prefs.getBoolean("hours", true)
        prefFormat = prefs.getString("format", "HH:mm dd/mm/yyyy")
        prefYourFormat = prefs.getString("yourFormat", "HH:mm dd/mm/yyyy")
        Log.d(TAG, "Getting preferences")
    }

    override fun onPause() {
        super.onPause()
        getPrefs()
        //Toast.makeText(context, "$prefDark , $prefTheme , $prefPeriod !", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "$prefDark , $prefTheme , $prefPeriod !")
    }
}