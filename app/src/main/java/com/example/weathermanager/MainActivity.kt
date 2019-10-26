package com.example.weathermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.util.Log
import kotlinx.android.synthetic.main.weather.*
import android.widget.ImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import android.view.View


class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"
    lateinit var tvTemp: TextView
    lateinit var tvImage: ImageView
    private lateinit var api: WeatherAPI.ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        tvTemp = temp
        tvImage = ivImage
        api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)
    }

    fun weather(v : View) {
        val lat = 49.22
        val lng = 28.409
        val units = "metric"
        val key = WeatherAPI.KEY

        Log.d(TAG, "OK")
        val callToday = api.getToday(lat, lng, units, key)
        callToday.enqueue(object : Callback<WeatherDay> {
            override fun onResponse(call: Call<WeatherDay>, response: Response<WeatherDay>) {
                Log.d(TAG, "onResponse")
                val data = response.body()
                Log.d(TAG,response.toString())

                if (response.isSuccessful) {
                    tvTemp.text = data!!.city + " " + data.tempWithDegree
                    Glide.with(this@MainActivity).load(data.iconUrl).into(tvImage)
                }
            }

            override fun onFailure(call: Call<WeatherDay>, t: Throwable) {
                Log.e(TAG, "onFailure")
                Log.e(TAG, t.toString())
            }
        })
    }
}
