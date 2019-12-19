package com.example.weathermanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weathermanager.R
import com.example.weathermanager.fragments.DashboardFragment
import com.example.weathermanager.fragments.HomeFragment
import com.example.weathermanager.fragments.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment(fragment:Fragment) : Boolean{
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .commit()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment:Fragment
        when(item.itemId){
            R.id.navigation_home -> fragment = HomeFragment()
            R.id.navigation_notifications -> fragment = NotificationsFragment()
            R.id.navigation_dashboard -> fragment = DashboardFragment()
        }
        return loadFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
}
