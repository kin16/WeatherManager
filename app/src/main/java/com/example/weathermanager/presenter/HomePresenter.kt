package com.example.weathermanager.presenter

import android.util.Log
import android.view.View
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomePresenter (model: Model){
    private val TAG = "HomePresenter"
    private val mModel = model
    private lateinit var weatherDay:WeatherDay

    fun weather(v:View) {
        mModel.getWeather()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "get weather")
                setViews(it, v)
            },{
                Log.e("s", "Error")
            }
            )
    }
    private fun setViews(weatherDay: WeatherDay, v:View): View{
        v.cardtemp.text = weatherDay.tempWithDegree
        v.carddescription.text = weatherDay.description
        v.pressure.text = weatherDay.pressure
        v.humidity.text = weatherDay.humidity
        v.windspeed.text = weatherDay.speed
        v.winddeg.text = weatherDay.deg
        v.clouding.text = weatherDay.all
        return v
    }
}