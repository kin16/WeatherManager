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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import com.example.weathermanager.presenter.ForecastPresenter
import com.example.weathermanager.view.MainActivity
import java.text.SimpleDateFormat



class ForecastFragment : Fragment() {
    private var TAG = "ForecastFragment"
    lateinit var rec: RecyclerView
    private lateinit var presenter: ForecastPresenter


    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "OnCreateView")
        (activity as MainActivity).navigation.menu.getItem(1).isChecked = true

        presenter = ForecastPresenter(Model(), this)

        val view = inflater.inflate(R.layout.fragment_forecast, null)
        rec = view.findViewById(R.id.rv)
        val manager = LinearLayoutManager(activity)
        rec.layoutManager = manager
        rec.setHasFixedSize(true)
        presenter.forecast()

        return view
    }

    class CardAdapter(persons: List<WeatherDay>) : RecyclerView.Adapter<CardAdapter.CardHolder>() {
        private val TAG = "CardAdapter"
        private var persons: List<WeatherDay>

        init {
            this.persons = persons
        }

        override fun getItemCount(): Int {
            Log.d(TAG, "Item count -> ${persons.size}")

            return persons.size
        }

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            Log.d(TAG, "OnBindViewHolder")

            val person = persons.get(position)

            val dateFormat = SimpleDateFormat("EEEEE dd.MM.yy - HH:mm")
            dateFormat.setTimeZone(person.date.getTimeZone())
            holder.date.text = dateFormat.format(person.date.time)


            holder.description.text = person.description
            holder.wind.text = "Скорость ветра: " + person.speed.toString()
            Log.d(TAG, "WIND: ${person.speed}")

            holder.pressure.text = "Давление: " + person.pressure
            holder.humidity.text = "Влажность: " + person.humidity

            holder.temp.text = person.tempWithDegree
            setImage(person, holder.icon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            Log.d(TAG, "OnCreateViewHolder")
            val v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.weather, parent, false)

            return CardHolder(v)
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

        class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           // var card:CardView
            var date: TextView
            var description: TextView
            var wind: TextView
            var pressure: TextView
            var humidity: TextView
            var temp: TextView
            var icon: ImageView

            init {
                //card = itemView.findViewById(R.id.card)
                date = itemView.findViewById(R.id.itemDate)
                description = itemView.findViewById(R.id.itemDescription)
                wind = itemView.findViewById(R.id.itemWind)
                pressure = itemView.findViewById(R.id.itemPressure)
                humidity = itemView.findViewById(R.id.itemHumidity)
                temp = itemView.findViewById(R.id.itemTemperature)
                icon = itemView.findViewById(R.id.itemIcon)
            }
        }
    }
}