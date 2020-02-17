package com.example.weathermanager.graphics

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weathermanager.R
import com.example.weathermanager.view.MainActivity
import com.github.paolorotolo.appintro.AppIntro


class CustomIntro : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFlowAnimation()
    }

    override fun init(savedInstanceState: Bundle?) {
        addSlide(SimpleSlide.newInstance(R.layout.intro_1))
        addSlide(SimpleSlide.newInstance(R.layout.intro_2))
        addSlide(SimpleSlide.newInstance(R.layout.intro_3))
    }

    private fun loadMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onNextPressed() { // Do something here
    }

    override fun onDonePressed() {
        finish()
        loadMainActivity()
    }

    override fun onSlideChanged() { // Do something here
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}