package com.example.weathermanager.model

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model{
    private var lat = 49.22
    private var lng = 28.409
    private val units = "metric"
    private val key = WeatherAPI.KEY
    private val TAG = "Model"
    private var api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)

    fun getWeather(context: Context): Single<WeatherDay> {
        Log.d(TAG, "Getting weather")
        return Single.create{ subscriber ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            lat = prefs.getFloat("lat", lat.toFloat()).toDouble()
            lng = prefs.getFloat("lng", lng.toFloat()).toDouble()

            Log.d(TAG, "OK")
            val callToday = api.getToday(lat, lng, units, key)
            callToday.enqueue(object : Callback<WeatherDay> {
                override fun onResponse(call: Call<WeatherDay>, response: Response<WeatherDay>) {
                    Log.d(TAG, "onResponse")
                    val data = response.body()
                    Log.d(TAG,response.toString())

                    if (response.isSuccessful) {
                        subscriber.onSuccess(data!!)
                    }
                }

                override fun onFailure(call: Call<WeatherDay>, t: Throwable) {
                    Log.e(TAG, "onFailure")
                    Log.e(TAG, t.toString())
                }
            })
        }
    }

    fun getForecast(context: Context): Single<WeatherForecast>{
        return Single.create{
            subscriber ->
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            lat = prefs.getFloat("lat", lat.toFloat()).toDouble()
            lng = prefs.getFloat("lng", lng.toFloat()).toDouble()

            val callForecast = api.getForecast(lat, lng, units, key)
            callForecast.enqueue(object : Callback<WeatherForecast> {
                override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                    Log.d(TAG, "onResponse")
                    val data = response.body()
                    Log.d(TAG,response.toString())

                    if (response.isSuccessful) {
                        subscriber.onSuccess(data!!)
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                    Log.e(TAG, "onFailure")
                    Log.e(TAG, t.toString())
                }
            })
        }
    }
}