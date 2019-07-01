package com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.day

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.chazo.personal.two.cleanarchitecturestudy.R
import kotlinx.android.synthetic.main.view_calendar_day.view.*

class CalendarDayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var day: Int = 0
    set(value) {
        field = value
        text_day?.text = day.toString()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_calendar_day, this, true)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        val colorRes = if(enabled) R.color.calendar_day else R.color.calendar_day_not_enabled
        text_day.setTextColor(ContextCompat.getColor(context, colorRes))
    }
}