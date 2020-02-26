package com.example.weathermanager.graphics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.Nullable


class SimpleSlide : Fragment(), ISlideBackgroundColorHolder {
    private var layoutResId = 0
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(ARG_LAYOUT_RES_ID)) layoutResId =
            arguments!!.getInt(ARG_LAYOUT_RES_ID)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun getDefaultBackgroundColor(): Int { // Return the default background color of the slide.
        return Color.parseColor("#000000")
    }

    override fun setBackgroundColor(@ColorInt backgroundColor: Int) { // Set the background color of the view within your slide to which the transition should be applied.
        if (container != null){
            container.setBackgroundColor(backgroundColor)
        }
    }

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"
        fun newInstance(layoutResId: Int): SimpleSlide {
            val sampleSlide = SimpleSlide()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            sampleSlide.arguments = args
            return sampleSlide
        }
    }
}