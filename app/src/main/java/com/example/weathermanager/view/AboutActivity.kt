package com.example.weathermanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.weathermanager.R

class AboutActivity : AppCompatActivity(){
    private val TAG = "AboutActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val prefDark = prefs.getString("prefDark", "auto")
        when (prefDark){
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }

        when(prefs.getString("theme", "Classic")){
            "New" -> setTheme(R.style.NewTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Classic" -> setTheme(R.style.ClassicTheme)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "OnOptionsItemSelected")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

        return true
    }

    override fun onBackPressed() {
        Log.d(TAG, "OnBackPressed")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}