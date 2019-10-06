package com.example.weathermanager

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.graphics.Color
import kotlinx.android.synthetic.main.config.*


class ConfigActivity : Activity() {
    val TAG:String = "ConfigActivity"
    var widgetID = AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var value: Intent
    companion object{
        const val WIDGET_PREF:String = "widget_pref"
        const val WIDGET_TEXT:String = "widget_text"
        const val WIDGET_COLOR:String = "widget_color"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        var intent = getIntent()
        var extras = intent.extras
        if (extras != null){
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
        value = Intent()
        value.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)

        setResult(RESULT_CANCELED, value)

        setContentView(R.layout.config)
    }

    fun onClick(v:View){
        val selRBColor = rgColor
            .checkedRadioButtonId
        var color = Color.RED

        when (selRBColor){
            radioRed.id -> color = Color.parseColor("#66ff0000")
            radioGreen.id -> color = Color.parseColor("#6600ff00")
            radioBlue.id -> color = Color.parseColor("#660000ff")
        }

        var preferences = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putString(WIDGET_TEXT + widgetID, etText.text.toString())
        editor.putInt(WIDGET_COLOR + widgetID, color)
        editor.commit()

        var appWidgetManager = AppWidgetManager.getInstance(this)
        MyWidget.updateWidget(this,appWidgetManager,preferences,widgetID)

        setResult(RESULT_OK, value)

        Log.d(TAG, "finish config" + widgetID)
        finish()
    }
}