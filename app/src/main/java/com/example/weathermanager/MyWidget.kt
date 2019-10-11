package com.example.weathermanager

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.bumptech.glide.request.target.CustomTarget

class MyWidget : AppWidgetProvider() {
    val TAG = "MyWidget"
    private lateinit var api : WeatherAPI.ApiInterface

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(TAG, "onEnabled")
        api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)
        getWeather(context!!, null, 0)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "onUpdate" + Arrays.toString(appWidgetIds))

        api = WeatherAPI.client!!.create(WeatherAPI.ApiInterface::class.java)

        for (item in appWidgetIds!!){
            getWeather(context!!, appWidgetManager, item)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds))
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(TAG, "onDisabled")
    }

    private fun getWeather(context: Context, appWidgetManager: AppWidgetManager?, widgetID: Int) {
        val lat = 49.22
        val lng = 28.409
        val units = "metric"
        val key = WeatherAPI.KEY

        Log.d(TAG, "OK")

        // get weather for today
        val callToday = api.getToday(lat, lng, units, key)
        callToday.enqueue(object : Callback<WeatherDay> {
            override fun onResponse(call: Call<WeatherDay>, response: Response<WeatherDay>) {
                Log.d(TAG, "onResponse")
                val data = response.body()
                var bitmap:Bitmap

                if (response.isSuccessful()) {
                    val remoteViews = RemoteViews(context.packageName, R.layout.widget)
                    remoteViews.setTextViewText(
                        R.id.temp,
                        data!!.city + " " + data.tempWithDegree
                    )

                    Glide.with(context)
                        .asBitmap()
                        .load(data.iconUrl)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onLoadCleared(placeholder: Drawable?) {
                            }

                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                            ) {
                                bitmap = resource
                                remoteViews.setImageViewBitmap(R.id.ivImage, bitmap)
                                appWidgetManager!!.updateAppWidget(widgetID, remoteViews)
                            }


                        })
                }
            }


            override fun onFailure(call: Call<WeatherDay>, t: Throwable) {
                Log.e(TAG, "onFailure")
                Log.e(TAG, t.toString())
            }
        })
    }
}