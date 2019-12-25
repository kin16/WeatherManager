package com.example.weathermanager.view

import android.os.Bundle
import com.example.weathermanager.fragments.SettingsFragment
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Display the fragment as the main content.

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }
}