package com.example.weathermanager.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import com.example.weathermanager.presenter.Presenter
import com.example.weathermanager.view.MainActivity
import java.text.SimpleDateFormat
import com.example.weathermanager.graphics.*
import com.example.weathermanager.presenter.AndroidDisposable


class ForecastFragment : Fragment() {
    private var TAG = "ForecastFragment"
    lateinit var rec: RecyclerView
    private lateinit var presenter: Presenter
    lateinit var progressDialog: MyProgressDialog
    private var disposable = AndroidDisposable()
    private var formatDate = "HH:mm dd/mm/yyyy"

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressDialog = MyProgressDialog.show(activity, null)

        Log.d(TAG, "OnCreateView")
        (activity as MainActivity).navigation.menu.getItem(1).isChecked = true

        presenter = Presenter(Model(context!!))

        val view = inflater.inflate(R.layout.fragment_forecast, null)
        rec = view.findViewById(R.id.rv)
        val manager = LinearLayoutManager(activity)
        rec.layoutManager = manager
        rec.setHasFixedSize(true)
        presenter.forecast(this, disposable)

        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        formatDate = prefs.getString("format", "HH:mm dd/mm/yyyy")!!
        if (formatDate == "your"){
            formatDate = prefs.getString("yourFormat", "HH:mm dd/mm/yyyy")!!
        }

        return view
    }

    override fun onPause() {
        disposable.dispose()
        super.onPause()
    }

   class CardAdapter(private var items: List<WeatherDay>, private val fragment: ForecastFragment) : RecyclerView.Adapter<CardAdapter.CardHolder>() {
       private val TAG = "CardAdapter"

       override fun getItemCount(): Int {
            Log.d(TAG, "Item count -> ${items.size}")

            return items.size
       }

       override fun onBindViewHolder(holder: CardHolder, position: Int) {
           Log.d(TAG, "OnBindViewHolder")

           val person = items[position]

           val dateFormat = SimpleDateFormat(fragment.formatDate)
           dateFormat.timeZone = (person.date.timeZone)
           holder.date.text = dateFormat.format(person.date.time)


           holder.description.text = person.description
           holder.wind.text = "Скорость ветра: " + person.speed.toString()
           Log.d(TAG, "WIND: ${person.speed}")

           holder.pressure.text = "Давление: " + person.pressure
           holder.humidity.text = "Влажность: " + person.humidity

           holder.temp.text = person.tempWithDegree
           setImage(person, holder.icon)

           if (fragment.progressDialog.isShowing){
               fragment.progressDialog.dismiss()
           }
       }

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
           Log.d(TAG, "OnCreateViewHolder")
           val v =
               LayoutInflater.from(parent.context).inflate(R.layout.weather, parent, false)

           return CardHolder(v)
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

       class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           var date: TextView = itemView.findViewById(R.id.itemDate)
           var description: TextView = itemView.findViewById(R.id.itemDescription)
           var wind: TextView = itemView.findViewById(R.id.itemWind)
           var pressure: TextView = itemView.findViewById(R.id.itemPressure)
           var humidity: TextView = itemView.findViewById(R.id.itemHumidity)
           var temp: TextView = itemView.findViewById(R.id.itemTemperature)
           var icon: ImageView = itemView.findViewById(R.id.itemIcon)
        }
   }
}