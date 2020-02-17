package com.example.weathermanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.weathermanager.fragments.SettingsFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weathermanager.R

class SettingsActivity : AppCompatActivity(){
    private val TAG = "SettingsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val prefTheme = prefs.getString("theme", "Grey")
        when(prefTheme){
            "Green" -> setTheme(R.style.GreenTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Blue" -> setTheme(R.style.BlueTheme)
            "Grey" -> setTheme(R.style.GreyTheme)
        }

        super.onCreate(savedInstanceState)
        // Display the fragment as the main content.
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "OnOptionsItemSelected")

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "OnBackPressed")

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}