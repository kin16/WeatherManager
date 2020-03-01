package com.example.weathermanager.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import com.example.weathermanager.fragments.ForecastFragment
import com.example.weathermanager.fragments.HomeFragment
import com.example.weathermanager.fragments.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val TAG = "MainActivity"
    lateinit var navigation: BottomNavigationView
    private var location: Location? = null
    private lateinit var prefs:SharedPreferences
    private val HOME = "HOME"
    private val OTHER = "OTHER"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val prefDark = prefs.getString("prefDark", "auto")
        when (prefDark){
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }

        val prefTheme = prefs.getString("theme", "Classic")
        when (prefTheme) {
            "New" -> setTheme(R.style.NewTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Classic" -> setTheme(R.style.ClassicTheme)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment(), HOME)

        navigation = findViewById(R.id.navigation)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when (prefTheme) {
                "New" -> {
                    navigation.itemTextColor = getColorStateList(R.color.newAccent)
                    navigation.itemIconTintList = getColorStateList(R.color.newAccent)
                }
                "Red" -> {
                    navigation.itemTextColor = getColorStateList(R.color.redAccent)
                    navigation.itemIconTintList = getColorStateList(R.color.redAccent)
                }
                "Classic" -> {
                    navigation.itemTextColor = getColorStateList(R.color.classicAccent)
                    navigation.itemIconTintList = getColorStateList(R.color.classicAccent)
                }
            }
        }
        navigation.setOnNavigationItemSelectedListener(this)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Log.d("BluetoothCommManager", "Providers: " + locationManager.allProviders)
        Log.d("BluetoothCommManager", "isProviderEnabled (Network): " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
        Log.d("BluetoothCommManager", "isProviderEnabled (GPS): " + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        Log.d("BluetoothCommManager", "isProviderEnabled (PASSIVE_PROVIDER): " + locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER));
        Log.d("BluetoothCommManager", "isProviderEnabled (FUSED_PROVIDER): " + locationManager.isProviderEnabled("fused"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                13)
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                13)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val locationListener: LocationListener = MyLocationListener(this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 1f, locationListener
                )
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 1f, locationListener
                )
            }

            Log.w(TAG, "loc -> $location")
        }else{
            location = Location("")
            location!!.latitude = prefs.getFloat("lat", 0f).toDouble()
            location!!.latitude = prefs.getFloat("lat", 0f).toDouble()
            Toast.makeText(this, "lat ${location!!.latitude}, lng ${location!!.longitude}", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadFragment(fragment: Fragment, name:String): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(fragment_container.id, fragment)

        val count = fragmentManager.backStackEntryCount
        if(name == OTHER){
            fragmentTransaction.addToBackStack(name)
        }

        fragmentTransaction.commit()
        Log.e(TAG, "loadFragment -> $fragment")

        fragmentManager.addOnBackStackChangedListener{object: FragmentManager.OnBackStackChangedListener{
            override fun onBackStackChanged() {
                if(fragmentManager.backStackEntryCount <= count){
                    fragmentManager.popBackStack(OTHER, 1)
                    fragmentManager.removeOnBackStackChangedListener(this)
                    navigation.menu.getItem(0).isChecked = true
                }
            }
        }}
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment: Fragment
        lateinit var name: String
        when (item.itemId) {
            R.id.navigation_home -> {
                getLocation()
                fragment = HomeFragment()
                name = HOME
            }
            R.id.navigation_notification -> {
                fragment = NotificationFragment()
                name = OTHER
            }
            R.id.navigation_forecast -> {
                fragment = ForecastFragment()
                name = OTHER
            }
        }
        Log.d(TAG, "OnNavigationItemSelected -> $fragment")

        return loadFragment(fragment, name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "OnOptionsItemSelected")

        return when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setLoc(loc: Location){
        location = loc
        val ed = prefs.edit()
        ed.putFloat("lat", loc.latitude.toFloat())
        ed.putFloat("lng", loc.longitude.toFloat())
        ed.apply()
        //Toast.makeText(this, "${prefs.getFloat("lng", 0f)}", Toast.LENGTH_SHORT).show()
    }

    private fun getLocation(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                val locationListener: LocationListener = MyLocationListener(this)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 1f, locationListener
                    )
                } else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 5000, 1f, locationListener
                    )
                }

                Log.w(TAG, "loc -> $location")
            }
        }
    }

    private class MyLocationListener(val context: MainActivity) : LocationListener {
        override fun onLocationChanged(loc: Location) {
            val TAG = "MyLocationListener"
           // Toast.makeText(context, "work", Toast.LENGTH_SHORT).show()
            Log.w(TAG, "Location changed: Lat: ${loc.latitude} Lng: ${loc.longitude}")
            val longitude = "Longitude: " + loc.longitude
            Log.w(TAG, longitude)
            val latitude = "Latitude: " + loc.latitude
            Log.w(TAG, latitude)
            /*------- To get city name from coordinates -------- */
            context.setLoc(loc)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
        }
    }
}