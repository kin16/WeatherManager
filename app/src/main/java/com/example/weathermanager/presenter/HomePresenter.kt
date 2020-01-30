package com.example.weathermanager.presenter

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat

class HomePresenter (model: Model){
    private val TAG = "HomePresenter"
    private val mModel = model
    //private lateinit var weatherDay:WeatherDay

    fun weather(v:View) {
        mModel.getWeather()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Getting weather")
                setViews(it, v)
            },{
                Log.e(TAG, "Error")
            }
            )
    }
    private fun setViews(weatherDay: WeatherDay, v:View): View{
        Log.d(TAG, "setViews")

        v.cardtemp.text = weatherDay.tempWithDegree
        v.carddescription.text = weatherDay.description
        v.pressure.text = "Давление   -   " + weatherDay.pressure
        v.humidity.text = "Влажность  -  " + weatherDay.humidity
        v.windspeed.text = "Скорость ветра  -  " + weatherDay.speed
        v.winddeg.text = "Направление ветра  -  " + weatherDay.deg
        v.clouding.text = "Облачность  -  " + weatherDay.all

        val dateFormat = SimpleDateFormat("HH:mm dd.MM.yy")
        dateFormat.setTimeZone(weatherDay.date.getTimeZone())
        v.textView.text = dateFormat.format(weatherDay.date.time)

        val image = v.findViewById<ImageView>(R.id.icon_weather)
        setImage(weatherDay, image)
        return v
    }

    fun setImage(data: WeatherDay, view: ImageView) {
        when (data.icon) {
            "01d" -> view.setImageResource(R.drawable.sun)
            "01n" -> view.setImageResource(R.drawable.moon)
            "02d" -> view.setImageResource(R.drawable.cloudy_sun)
            "02n" -> view.setImageResource(R.drawable.cloudy_moon)
            "03d" -> view.setImageResource(R.drawable.clouds)
            "03n" -> view.setImageResource(R.drawable.clouds)
            "04d" -> view.setImageResource(R.drawable.hard_clouds)
            "04n" -> view.setImageResource(R.drawable.hard_clouds)
            "09d" -> view.setImageResource(R.drawable.shower_rain)
            "09n" -> view.setImageResource(R.drawable.shower_rain)
            "10d" -> view.setImageResource(R.drawable.rain)
            "10n" -> view.setImageResource(R.drawable.rain)
            "11d" -> view.setImageResource(R.drawable.storm)
            "11n" -> view.setImageResource(R.drawable.storm)
            "12d" -> view.setImageResource(R.drawable.winter)
            "12n" -> view.setImageResource(R.drawable.winter)
            "13d" -> view.setImageResource(R.drawable.mist)
            "13n" -> view.setImageResource(R.drawable.mist)
        }
    }
}