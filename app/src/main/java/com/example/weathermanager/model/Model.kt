package com.example.weathermanager.model

import android.util.Log
import com.bumptech.glide.Glide
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model{

    private val TAG = "Model"
    private var api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)

    fun getWeather(): Single<WeatherDay> {
        return Single.create{ subscriber ->
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
}