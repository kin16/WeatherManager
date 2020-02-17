package com.example.weathermanager.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
    var location: Location? = null
    private lateinit var prefs:SharedPreferences

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OnCreate")

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val prefTheme = prefs.getString("theme", "Grey")
        when (prefTheme) {
            "Green" -> setTheme(R.style.GreenTheme)
            "Red" -> setTheme(R.style.RedTheme)
            "Blue" -> setTheme(R.style.BlueTheme)
            "Grey" -> setTheme(R.style.GreyTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())

        navigation = findViewById(R.id.navigation)
        when (prefTheme) {
            "Green" -> navigation.background = getDrawable(R.color.greenPrimary)
            "Red" -> navigation.background = getDrawable(R.color.redPrimary)
            "Blue" -> navigation.background = getDrawable(R.color.bluePrimary)
            "Grey" -> navigation.background = getDrawable(R.color.greyPrimary)
        }
        navigation.setOnNavigationItemSelectedListener(this)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        Log.d("BluetoothCommManager", "Providers: " + locationManager.getAllProviders());
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

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(fragment_container.id, fragment)
            .addToBackStack(null)
            .commit()
        Log.e(TAG, "loadFragment -> $fragment")
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var fragment: Fragment
        when (item.itemId) {
            R.id.navigation_home -> {
                getLocation()
                fragment = HomeFragment()
            }
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

    fun setLoc(loc: Location){
        location = loc
        val ed = prefs.edit()
        ed.putFloat("lat", loc.latitude.toFloat())
        ed.putFloat("lng", loc.longitude.toFloat())
        ed.apply()
        //Toast.makeText(this, "${prefs.getFloat("lng", 0f)}", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val count = getSupportFragmentManager().getBackStackEntryCount()
        super.onBackPressed()

        if(count == 1) {
            finish()
        }
    }

    fun getLocation(){
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

    private class MyLocationListener(context: MainActivity) : LocationListener {
        val context = context
        override fun onLocationChanged(loc: Location) {
            val TAG = "MyLocationListener"
           // Toast.makeText(context, "work", Toast.LENGTH_SHORT).show()
            Log.w(TAG, "Location changed: Lat: ${loc.getLatitude().toString()} Lng: ${loc.getLongitude()}")
            val longitude = "Longitude: " + loc.getLongitude()
            Log.w(TAG, longitude)
            val latitude = "Latitude: " + loc.getLatitude()
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