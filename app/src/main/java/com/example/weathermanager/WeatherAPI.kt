package com.example.weathermanager

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object WeatherAPI {
    var KEY = "7054efcfe4c9368e89f2e16b9ac6565a"
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.retrofit
        }


    interface ApiInterface{
        @GET("weather")
        fun getToday(
            @Query("lat") lat: Double?,
            @Query("lon") lon: Double?,
            @Query("units") units: String,
            @Query("appid") appid: String
        ): Call<WeatherDay>
    }
}