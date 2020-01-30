package com.example.weathermanager.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class WeatherDay(
    @field:SerializedName("main")
    private val temp: WeatherTemp, @field:SerializedName("weather")
    private val desctiption: List<WeatherDescription>,
    @field:SerializedName("wind") private val windSpeed: WindSpeed,
    @field:SerializedName("clouds") private val clouds:Clouds
    //@field:SerializedName("wind") private val rain: Rain,
    //@field:SerializedName("wind") private val snow: Snow
) {

    @SerializedName("name")
    val city: String? = null

    @SerializedName("dt")
    private val timestamp: Long = 0

    val date: Calendar
        get() {
            val date = Calendar.getInstance()
            date.setTimeInMillis(timestamp * 1000)
            return date
        }

    val tempMin: String
        get() = temp.temp_min.toString()

    val tempMax: String
        get() = temp.temp_max.toString()

    val tempInteger: String
        get() = temp.temp!!.toInt().toString()

    val tempInt: Int
        get() = temp.temp!!.toInt()

    val tempWithDegree: String
        get() = temp.temp!!.toInt().toString() + "\u00B0"

    val icon: String?
        get() = desctiption[0].icon

    val description: String?
        get() = desctiption[0].description

    val pressure: String?
        get() = temp.pressure.toString()

    val humidity: String?
        get() = temp.humidity.toString()

    val speed: String?
        get() = windSpeed.speed.toString()

    val deg: String?
        get() = windSpeed.deg.toString()

    val all:String?
        get() = clouds.all.toString()
    //val hrain:String?
    //    get() = rain.h.toString()
    //val hsnow:String?
    //    get() = snow.h.toString()

    val iconUrl: String
        get() = "http://openweathermap.org/img/w/" + desctiption[0].icon + ".png"

    inner class WeatherTemp {
        internal var temp: Double? = null
        internal var pressure: Double? = null
        internal var humidity: Double? = null
        internal var temp_min: Double? = null
        internal var temp_max: Double? = null
    }

    inner class Clouds{
        internal var all:Double? = null
    }

    inner class WindSpeed{
        internal var speed: Double? = null
        internal var deg: Double? = null
    }

    inner class Rain{
        internal var h:Double? = null
    }
    inner class Snow{
        internal var h:Double? = null
    }

    inner class WeatherDescription {
        @SerializedName("description")
        internal var description: String? = null
        @SerializedName("icon")
        internal var icon: String? = null
    }

    fun getTemp(): String {
        return temp.temp.toString()
    }
}