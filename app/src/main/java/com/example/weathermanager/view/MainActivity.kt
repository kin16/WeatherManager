package com.example.weathermanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.weather.*
import android.widget.ImageView
import android.view.View
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.presenter.Presenter


class MainActivity : AppCompatActivity() {
    var TAG = "MainActivity"
    lateinit var tvTemp: TextView
    lateinit var tvImage: ImageView
    lateinit var presenter: Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        presenter = Presenter(Model(), this)

        tvTemp = temp
        tvImage = ivImage
    }

    fun weather(v : View) {
        presenter.weather()
    }
}
