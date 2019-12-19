package com.example.weathermanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.widget.Button
import com.example.weathermanager.R


class NotificationsFragment : Fragment(),View.OnClickListener  {
    private var mButton:Button? = null
    var DIALOG_TIME = "Time"

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(com.example.weathermanager.R.layout.fragment_notifications, null)
        mButton = v.findViewById(R.id.button)
        mButton!!.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View?) {
        val fragment = TimePickerFragment()
        fragment.show(fragmentManager!!, DIALOG_TIME)
    }
}