package com.example.weathermanager.presenter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import com.example.weathermanager.model.Model
import com.example.weathermanager.view.MainActivity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.weathermanager.R
import com.example.weathermanager.model.WeatherDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class Presenter(model:Model, activity: MainActivity){
    val activity = activity
    val model = model

    fun weather() {
        val subscriber = model.getWeather()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setViews(it)
                setImage(it, activity.tvImage)
            },{
                Log.e("s", "Error")
            }
            )
    }

    fun forecast() {
        val subscriber = model.getForecast()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{
                Log.e("s", "Error")
            }
            )
    }

    fun setViews(data: WeatherDay) {
        activity.tvTemp.text = data.city + " " + data.tempInt
        Glide.with(activity)
            .asBitmap()
            .load(data.iconUrl)
            .into(activity.tvImage)
        //notification(data.tempInt, activity)
    }

    fun setImage(data: WeatherDay, view: ImageView){
        when(data.icon){
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

    companion object {
        @JvmStatic
        fun notification(temp: Int, context: Context) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // I would suggest that you use IMPORTANCE_DEFAULT instead of IMPORTANCE_HIGH
                val channel =
                    NotificationChannel("ID", "Weather", NotificationManager.IMPORTANCE_HIGH)
                channel.enableVibration(true)
                channel.lightColor = Color.BLUE
                channel.enableLights(true)

                channel.setShowBadge(true)
                notificationManager.createNotificationChannel(channel)
            }

            val bigText = NotificationCompat.BigTextStyle()
            bigText.bigText("Now the temperature is " + temp)
            bigText.setBigContentTitle("Temperature has dropped")
            bigText.setSummaryText("Temperature detail")

            val ii = Intent(context.getApplicationContext(), MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, ii, 0)


            val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Title")
                .setContentText("Notification text")
                .setChannelId("ID")
                .setStyle(bigText)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notification = builder.build()

            notificationManager.notify(1, notification)
        }
    }
}