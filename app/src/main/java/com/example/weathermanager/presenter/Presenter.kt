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
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.weathermanager.R
import com.example.weathermanager.model.NotificationService
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