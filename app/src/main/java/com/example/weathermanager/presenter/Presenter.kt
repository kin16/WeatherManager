package com.example.weathermanager.presenter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.example.weathermanager.R
import com.example.weathermanager.fragments.ForecastFragment
import com.example.weathermanager.fragments.HomeFragment
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import com.example.weathermanager.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Presenter(private val model: Model){
    private var TAG = "Presenter"


    fun weather(v: View, context: Context, fragment: HomeFragment, disposable: AndroidDisposable) {
        val disp = model.getWeather()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Getting weather")
                setViews(it, v, context, fragment)
            },{
                Log.e(TAG, "Error")
            }
            )
        disposable.add(disp)
    }

    fun forecast(fragment:ForecastFragment, disposable: AndroidDisposable) {
        val disp = model.getForecast()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG, "Getting forecast")
                val adapter = ForecastFragment.CardAdapter(it.items, fragment)
                fragment.rec.adapter = adapter
            },{
                Log.e(TAG, "Error")
            }
            )
        disposable.add(disp)
    }

    private fun setViews(weatherDay: WeatherDay, v:View, context: Context, fragment:HomeFragment): View{
        Log.d(TAG, "setViews")

        v.cardtemp.text = weatherDay.tempWithDegree
        v.carddescription.text = weatherDay.description
        v.pressure.text = "Давление   -   " + weatherDay.pressure
        v.humidity.text = "Влажность  -  " + weatherDay.humidity
        v.windspeed.text = "Скорость ветра  -  " + weatherDay.speed
        v.winddeg.text = "Направление ветра  -  " + weatherDay.deg
        v.clouding.text = "Облачность  -  " + weatherDay.all

        val dateFormat = SimpleDateFormat("HH:mm")
        dateFormat.timeZone = weatherDay.date.timeZone
        v.textView.text = "Дата обновления: " + dateFormat.format(weatherDay.date.time)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val city = setCity(prefs.getFloat("lat", 0f).toDouble(),
            prefs.getFloat("lng", 0f).toDouble(), context)

        if (city != null && city != "") {
            v.tcity.text = city
        }else{
            v.tcity.text = "Город не определен"
            Toast.makeText(context, "Включите gps и перезапустите приложение", Toast.LENGTH_LONG).show();
        }

        val image = v.findViewById<ImageView>(R.id.icon_weather)
        setImage(weatherDay, image)

        if(fragment.progressDialog.isShowing){
            fragment.progressDialog.dismiss()
        }
        return v
    }

    private fun setCity(lat:Double, lng:Double, context: Context): String?{
        var cityName: String? = null
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(
                lat, lng, 1
            )
            if (addresses.isNotEmpty()) {
                cityName = addresses[0].locality
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //val s = (lng.toString() + "\n" + lat + "\n\nMy Current City is: " + cityName)

        //Toast.makeText(context, s, Toast.LENGTH_LONG).show()

        return cityName
    }

    private fun setImage(data: WeatherDay, view: ImageView) {
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

    companion object {
        @JvmStatic
        fun weather(context: Context){
            var temp = ""
            val disp = Model(context).getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TimeReceiver", "Getting weather")
                    temp = it.tempWithDegree
                },{
                    Log.e("TimeReceiver", "Error")
                })
            notification(temp, context)
        }

        fun notification(temp: String, context: Context) {
            Log.e("TimeReceiver", "New Notification showing")

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // I would suggest that you use IMPORTANCE_DEFAULT instead of IMPORTANCE_HIGH
                val channel =
                    NotificationChannel("ID", "NAME", NotificationManager.IMPORTANCE_HIGH)
                channel.enableVibration(true)
                channel.lightColor = Color.BLUE
                channel.enableLights(true)

                channel.setShowBadge(true)
                notificationManager.createNotificationChannel(channel)
            }

            Log.d("TimeReceiver", "working")

            Log.d("TimeReceiver", "Temp = $temp")
            val bigText = NotificationCompat.BigTextStyle()
            bigText.bigText("Now the temperature is $temp")
            bigText.setBigContentTitle("Temperature changed")
            bigText.setSummaryText("Temperature detail")

            val ii = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, ii, 0)


            val builder = NotificationCompat.Builder(context, "")
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