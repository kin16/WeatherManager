package com.example.weathermanager.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class WeatherDay(
    @field:SerializedName("main")
    private val temp: WeatherTemp, @field:SerializedName("weather")
    private val desctription: List<WeatherDescription>,
    @field:SerializedName("wind") private val windSpeed: WindSpeed,
    @field:SerializedName("clouds") private val clouds:Clouds) {

    @SerializedName("name")
    val city: String? = null

    @SerializedName("dt")
    private val timestamp: Long = 0

    val date: Calendar
        get() {
            val date = Calendar.getInstance()
            date.timeInMillis = timestamp * 1000
            return date
        }

    val tempInt: Int
        get() = temp.temp!!.toInt()

    val tempWithDegree: String
        get() = temp.temp!!.toInt().toString() + "\u00B0"

    val icon: String?
        get() = desctription[0].icon

    val description: String?
        get() = desctription[0].description

    val pressure: String?
        get() = temp.pressure!!.toInt().toString()

    val humidity: String?
        get() = temp.humidity!!.toInt().toString()

    val speed: String?
        get() = windSpeed.speed!!.toInt().toString()

    val deg: String?
        get() = windSpeed.deg!!.toInt().toString()

    val all:String?
        get() = clouds.all!!.toInt().toString()

    val iconUrl: String
        get() = "http://openweathermap.org/img/w/" + desctription[0].icon + ".png"

    inner class WeatherTemp {
        internal var temp: Double? = null
        internal var pressure: Double? = null
        internal var humidity: Double? = null
    }

    inner class Clouds{
        internal var all:Double? = null
    }

    inner class WindSpeed{
        internal var speed: Double? = null
        internal var deg: Double? = null
    }

    inner class WeatherDescription {
        @SerializedName("description")
        internal var description: String? = null
        @SerializedName("icon")
        internal var icon: String? = null
    }
}