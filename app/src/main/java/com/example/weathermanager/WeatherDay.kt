package com.example.weathermanager

import com.google.gson.annotations.SerializedName
import java.util.*


class WeatherDay(
    @field:SerializedName("main")
    private val temp: WeatherTemp, @field:SerializedName("weather")
    private val desctiption: List<WeatherDescription>
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

    val tempWithDegree: String
        get() = temp.temp!!.toInt().toString() + "\u00B0"

    val icon: String?
        get() = desctiption[0].icon

    val iconUrl: String
        get() = "http://openweathermap.org/img/w/" + desctiption[0].icon + ".png"

    inner class WeatherTemp {
        internal var temp: Double? = null
        internal var temp_min: Double? = null
        internal var temp_max: Double? = null
    }

    inner class WeatherDescription {
        internal var icon: String? = null
    }

    fun getTemp(): String {
        return temp.temp.toString()
    }
}