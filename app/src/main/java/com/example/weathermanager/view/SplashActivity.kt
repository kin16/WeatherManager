package com.example.weathermanager.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.weathermanager.graphics.CustomIntro


class SplashActivity : AppCompatActivity(){
    private val FIRST_START = "first."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getPrefs: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(baseContext)
        val isFirstStart = getPrefs.getBoolean(FIRST_START, true)

        if (isFirstStart) {
            val t = Thread(Runnable {
                val i = Intent(this, CustomIntro::class.java)
                startActivity(i)
                finish()
                val e = getPrefs.edit()
                e.putBoolean(FIRST_START, false)
                e.apply()
            })
            // Start the thread
            t.start()
        }else {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
