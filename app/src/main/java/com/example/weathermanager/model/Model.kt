package com.example.weathermanager.model

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model(context: Context){
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private var lat = prefs.getFloat("lat", 0.0F).toDouble()
    private var lng = prefs.getFloat("lng", 0.0F).toDouble()
    private val units = "metric"
    private val key = prefs.getString("key", WeatherAPI.KEY)
    private val TAG = "Model"
    private var api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)

    fun getWeather(): Single<WeatherDay> {
        Log.d(TAG, "Getting weather")
        return Single.create{ subscriber ->
            Log.d(TAG, "OK")

            val callToday = if (key != null) {
                api.getToday(lat, lng, units, key)
            }else{
                api.getToday(lat, lng, units, WeatherAPI.KEY)
            }

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

    fun getForecast(): Single<WeatherForecast>{
        return Single.create{
            subscriber ->
            val callForecast = if (key != null) {
                api.getForecast(lat, lng, units, key)
            }else{
                api.getForecast(lat, lng, units, WeatherAPI.KEY)
            }
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