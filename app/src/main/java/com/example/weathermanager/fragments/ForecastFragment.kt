package com.example.weathermanager.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.model.WeatherDay
import com.example.weathermanager.presenter.ForecastPresenter
import java.util.*


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

        presenter = ForecastPresenter(Model(), this)

        val view = inflater.inflate(R.layout.fragment_forecast, null)
        rec = view.findViewById(R.id.rv)
        val manager = GridLayoutManager(activity, 2)
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

            holder.ct.text = persons.get(position).tempWithDegree
            holder.co.text = persons.get(position).description
            //setImage(persons.get(position), holder.ci)
            holder.cd.text = persons.get(position).date.time.toString()
            val rnd = Random()
            val currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            holder.cv.setCardBackgroundColor(currentColor)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
            Log.d(TAG, "OnCreateViewHolder")

            val v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.weather, parent, false)

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