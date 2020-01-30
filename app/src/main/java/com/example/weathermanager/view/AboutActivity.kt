package com.example.weathermanager.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weathermanager.R

class AboutActivity : AppCompatActivity(){
    private val TAG = "AboutActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val prefTheme = prefs.getString("theme", "Green")
        when(prefTheme){
            "Green" -> setTheme(R.style.GreenTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Blue" -> setTheme(R.style.BlueTheme)
            "Grey" -> setTheme(R.style.GreyTheme)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}