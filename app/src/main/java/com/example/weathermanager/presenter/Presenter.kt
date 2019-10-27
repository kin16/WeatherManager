package com.example.weathermanager.presenter

import com.example.weathermanager.model.Model
import com.example.weathermanager.view.MainActivity
import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.example.weathermanager.model.NotificationService
import com.example.weathermanager.model.WeatherDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Presenter(model:Model, activity: MainActivity){
    val activity = activity
    val model = model

    fun weather() {
        val subscription = model.getWeather()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setViews(it)
            },{
                Log.e("s", "Error")
            }
            )
    }

    fun setViews(data: WeatherDay){
        activity.tvTemp.text = data.city + " " + data.tempInt
        Glide.with(activity)
            .asBitmap()
            .load(data.iconUrl)
            .into(activity.tvImage)

        val intent = Intent(activity, NotificationService::class.java)
        activity.startService(intent)
    }
}