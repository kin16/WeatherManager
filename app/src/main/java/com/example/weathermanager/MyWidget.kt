package com.example.weathermanager

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import java.util.*




class MyWidget : AppWidgetProvider() {


    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(TAG, "onEnabled")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d(TAG, "onDisabled" + Arrays.toString(appWidgetIds))

        val preferences =
            context!!.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)

        for (item in appWidgetIds!!) {
            updateWidget(context, appWidgetManager!!, preferences, item)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "onDeleted " + Arrays.toString(appWidgetIds))

        val editor = context.getSharedPreferences(
            ConfigActivity.WIDGET_PREF,Context.MODE_PRIVATE).edit()

        for (item in appWidgetIds){
            editor.remove(ConfigActivity.WIDGET_TEXT + item)
            editor.remove(ConfigActivity.WIDGET_COLOR + item)
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(TAG, "onDisabled")
    }

    companion object {
        const val TAG: String = "MyWidget"

        @JvmStatic
        fun updateWidget(
            context: Context?, appWidgetManager: AppWidgetManager,
            preferences: SharedPreferences, widgetId: Int
        ) {
            Log.d(TAG, "updateWidget $widgetId")

            // Читаем параметры Preferences
            val widgetText = preferences.getString(ConfigActivity.WIDGET_TEXT + widgetId, null) ?: return
            val widgetColor = preferences.getInt(ConfigActivity.WIDGET_COLOR + widgetId, 0)

            // Настраиваем внешний вид виджета
            val widgetView = RemoteViews(
                context!!.getPackageName(),
                R.layout.widget
            )
            widgetView.setTextViewText(R.id.textv, widgetText)
            widgetView.setInt(R.id.textv, "setBackgroundColor", widgetColor)

            // Обновляем виджет
            appWidgetManager.updateAppWidget(widgetId, widgetView)

        }
    }
}

