package com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.day.CalendarDayView
import com.chazo.personal.two.cleanarchitecturestudy.utils.extensions.calendarIndex
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_month.*
import timber.log.Timber
import java.time.LocalDate
import kotlin.math.abs

class MonthFragment : Fragment() {
    companion object {
        private const val EXTRA_YEAR = "extra_year"
        private const val EXTRA_MONTH = "extra_month"
        fun newInstance(year: Int, month: Int): MonthFragment = MonthFragment().apply {
            arguments = Bundle().also {
                it.putInt(EXTRA_YEAR, year)
                it.putInt(EXTRA_MONTH, month)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_month, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()
    }

    private fun setupCalendar() {
        arguments?.let {
            val year = it.getInt(EXTRA_YEAR)
            val month = it.getInt(EXTRA_MONTH)

            val lastMonthLocalDate = LocalDate.of(year, if (month == 1) 12 else month - 1, 1)
            val localDate = LocalDate.of(year, month, 1)

            val startIndex = localDate.dayOfWeek.calendarIndex
            var index = 0
            for (row in 0 until layout_days.rowCount) {
                for (col in 0 until layout_days.columnCount) {
                    val day = index + 1 - if (row == 0) startIndex else 0
                    val dayView = CalendarDayView(requireContext())
                    when {
                        day < 1 -> {
                            dayView.day = lastMonthLocalDate.lengthOfMonth() - abs(day)
                            dayView.isEnabled = false
                        }
                        day > localDate.lengthOfMonth() -> {
                            dayView.day = day - localDate.lengthOfMonth()
                            dayView.isEnabled = false
                        }
                        else -> {
                            dayView.day = day
                        }
                    }
                    Timber.d("month=$month day=$day row=$row col=$col")
                    val layoutParams = GridLayout.LayoutParams()
                    layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    layout_days.addView(dayView, index, layoutParams)
                    index++
                }
            }
        }
    }
}