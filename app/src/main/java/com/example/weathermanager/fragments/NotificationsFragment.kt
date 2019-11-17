package com.example.weathermanager.fragments

import   android . os . Bundle ;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.weathermanager.R

/**
 * Created by Belal on 1/23/2018.
 */

class NotificationsFragment : Fragment() {
    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, null)
    }
}