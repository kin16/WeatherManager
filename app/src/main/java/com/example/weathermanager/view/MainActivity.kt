package com.example.weathermanager.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import com.example.weathermanager.fragments.ForecastFragment
import com.example.weathermanager.fragments.HomeFragment
import com.example.weathermanager.fragments.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    private val TAG = "MainActivity"
    lateinit var navigation:BottomNavigationView

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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())

        navigation = findViewById(R.id.navigation)
        when(prefTheme) {
            "Green" -> navigation.background = getDrawable(R.color.greenPrimary)
            "Red" -> navigation.background = getDrawable(R.color.redPrimary)
            "Blue" -> navigation.background = getDrawable(R.color.bluePrimary)
            "Grey" -> navigation.background = getDrawable(R.color.greyPrimary)
        }
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment(fragment:Fragment) : Boolean{
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .addToBackStack(null)
            .commit()
        Log.d(TAG, "loadFragment -> $fragment")
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment:Fragment
        when(item.itemId){
            R.id.navigation_home -> fragment = HomeFragment()
            R.id.navigation_notification -> fragment = NotificationFragment()
            R.id.navigation_forecast -> fragment = ForecastFragment()
        }

        Log.d(TAG, "OnNavigationItemSelected -> $fragment")

        return loadFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "OnOptionsItemSelected")

        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val count = getSupportFragmentManager().getBackStackEntryCount()

        if(count == 1) {
            super.onBackPressed()
            Toast.makeText(this, "Finishing... $count", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Count -> $count", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
    }
}