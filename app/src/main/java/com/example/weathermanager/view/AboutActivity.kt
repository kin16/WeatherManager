package com.example.weathermanager.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weathermanager.R

class AboutActivity : AppCompatActivity(){
    private val TAG = "AboutActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}