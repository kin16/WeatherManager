package com.example.weathermanager.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.weathermanager.R
import com.example.weathermanager.graphics.MyProgressDialog
import com.example.weathermanager.model.Model
import com.example.weathermanager.presenter.AndroidDisposable
import com.example.weathermanager.presenter.Presenter
import com.example.weathermanager.view.MainActivity

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private lateinit var presenter: Presenter
    lateinit var progressDialog: MyProgressDialog
    private var disposable = AndroidDisposable()


    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,  "OnCreateView")
        (activity as MainActivity).navigation.menu.getItem(0).isChecked = true

        progressDialog = MyProgressDialog.show(activity, null)

        presenter = Presenter(Model(context!!))
        val v = inflater.inflate(R.layout.fragment_home, null)
        presenter.weather(v, context!!, this, disposable)
        return v
    }


    override fun onPause() {
        disposable.dispose()
        super.onPause()
    }
}