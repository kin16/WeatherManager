package com.example.weathermanager.model

import com.google.gson.annotations.SerializedName


class WeatherForecast(
    @field:SerializedName("list")
    val items: List<WeatherDay>
)
