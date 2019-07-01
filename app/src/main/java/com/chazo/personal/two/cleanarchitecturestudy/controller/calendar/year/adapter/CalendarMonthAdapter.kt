package com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.year.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.month.MonthFragment

class CalendarMonthAdapter(var year: Int, fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        const val FLAG_PREVIOUS = 0
        const val FLAG_NEXT = 13

    }


    override fun getItemCount(): Int = 14

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            FLAG_PREVIOUS -> MonthFragment.newInstance(year - 1, 12)
            FLAG_NEXT -> MonthFragment.newInstance(year + 1, 1)
            else -> MonthFragment.newInstance(year, position)
        }
    }
}