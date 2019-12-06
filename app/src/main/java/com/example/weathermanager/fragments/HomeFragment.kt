package com.example.weathermanager.fragments

import   android . os . Bundle ;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.weathermanager.R
import com.example.weathermanager.model.Model
import com.example.weathermanager.presenter.HomePresenter
import com.example.weathermanager.presenter.Presenter

class HomeFragment : Fragment() {
    private var TAG = "HomeFragment"
    private lateinit var presenter: HomePresenter

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = HomePresenter(Model())
        var v = inflater.inflate(R.layout.fragment_home, null)
        presenter.weather(v)
        return v
    }
}