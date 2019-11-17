package com.example.weathermanager.view

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import com.example.weathermanager.presenter.Presenter
import kotlinx.android.synthetic.main.rec.*
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weathermanager.R
import java.util.*


class MainActivity : Activity() {
    private var TAG = "MainActivity"
    lateinit var rec: RecyclerView
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rec)

        presenter = Presenter(Model(), this)

        rec = rv as RecyclerView
        val manager = GridLayoutManager(this, 2)
        rec.layoutManager = manager
        rec.setHasFixedSize(true)
        presenter.forecast()
    }

    class CardAdapter(persons: List<WeatherDay>) : RecyclerView.Adapter<CardAdapter.CardHolder>() {

        private var persons: List<WeatherDay>

        init {
            this.persons = persons
        }

        override fun getItemCount(): Int {
            return persons.size
        }

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            holder.ct.text = persons.get(position).tempWithDegree
            holder.co.text = persons.get(position).description
            //setImage(persons.get(position), holder.ci)
            holder.cd.text = persons.get(position).date.time.toString()
            val rnd = Random()
            var currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            holder.cv.setCardBackgroundColor(currentColor)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather, parent, false)
            return CardHolder(v)
        }
        class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cv: CardView
            var ct: TextView
            var co: TextView
            var cd: TextView

            init {
                cv = itemView.findViewById(R.id.card) as CardView
                ct = itemView.findViewById(R.id.cardtemp) as TextView
                co = itemView.findViewById(R.id.carddescription) as TextView
                cd = itemView.findViewById(R.id.carddate) as TextView

            }
        }
    }
}

