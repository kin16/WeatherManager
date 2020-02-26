package com.example.weathermanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weathermanager.R

class AboutActivity : AppCompatActivity(){
    private val TAG = "AboutActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        when(prefs.getString("theme", "Grey")){
            "Green" -> setTheme(R.style.GreenTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Blue" -> setTheme(R.style.BlueTheme)
            "Grey" -> setTheme(R.style.GreyTheme)
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
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