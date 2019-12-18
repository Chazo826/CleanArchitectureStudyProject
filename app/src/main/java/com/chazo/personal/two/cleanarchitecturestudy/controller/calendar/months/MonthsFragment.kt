package com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.months

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.year.adapter.CalendarMonthAdapter
import kotlinx.android.synthetic.main.fragment_months.*
import timber.log.Timber
import java.time.LocalDate

class MonthsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_months, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupMonths()
    }

    private fun setupMonths() {
        val localDate: LocalDate = LocalDate.now()
        text_year.text = getString(R.string.year, localDate.year)
        setupMonthsAdapter(localDate.year)
        viewpager2_months.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                if(position == CalendarMonthAdapter.FLAG_PREVIOUS || position == CalendarMonthAdapter.FLAG_NEXT) {
                    updateMonths(position)
//                }


            }
        })
        Timber.d("month value = ${localDate.monthValue}")
        viewpager2_months.setCurrentItem(localDate.monthValue, false)
    }

    private fun setupMonthsAdapter(year: Int) {
        fragmentManager?.let {
            viewpager2_months.adapter = CalendarMonthAdapter(year, it, lifecycle)
        }
    }

    private fun updateMonths(position: Int) {
        val adapter = viewpager2_months.adapter as CalendarMonthAdapter
        val year = adapter.year

        when(position) {
            CalendarMonthAdapter.FLAG_PREVIOUS -> {
                adapter.year = year - 1
                viewpager2_months.setCurrentItem(12, false)
                adapter.notifyDataSetChanged()
            }
            CalendarMonthAdapter.FLAG_NEXT -> {
                adapter.year = year + 1
                viewpager2_months.setCurrentItem(1, false)
                adapter.notifyDataSetChanged()
            }
        }

        text_month.text = getString(R.string.month,
            when (position) {
                0 -> 12
                13 -> 1
                else -> position
            }
        )

        updateYear(adapter.year)
    }

    private fun updateYear(year: Int) {
        text_year.text = getString(R.string.year, year)
    }
}