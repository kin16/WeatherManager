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
        v.pressure.text = "Атмосферное давление   -   " + weatherDay.pressure
        v.humidity.text = "Влажность  -  " + weatherDay.humidity
        v.windspeed.text = "Скорость ветра  -  " + weatherDay.speed
        v.winddeg.text = "Направление ветра  -  " + weatherDay.deg
        v.clouding.text = "Облачность, %  -  " + weatherDay.all
        //v.snow.text = "Обьем снега, мм" + weatherDay.hsnow
        //v.rain.text = "Обьем дождя, мм" + weatherDay.hrain
        //v.textView.text = weatherDay.date.toString()
        return v
    }
}