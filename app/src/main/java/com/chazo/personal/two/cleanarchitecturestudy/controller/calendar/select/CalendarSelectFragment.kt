package com.chazo.personal.two.cleanarchitecturestudy.controller.calendar.select

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.constant.RC_AUTH_PERMISSION
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.GoogleCalendarRepository
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.services.calendar.model.CalendarListEntry
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_calendar_select.*
import javax.inject.Inject

class CalendarSelectFragment : DaggerFragment() {
    @Inject
    lateinit var googleCalendarRepository: GoogleCalendarRepository

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_calendar_select, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCalendars()
    }

    // todo: movew method in presenter
    private fun getCalendars() {
        googleCalendarRepository.getCalendarList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.items }
            .doFinally { progress_loading?.visibility = View.GONE }
            .subscribe({
                it?.let { list ->
                    setCalendars(list)
                }
            }, {
                when (it) {
                    is UserRecoverableAuthIOException -> startActivityForResult(it.intent, RC_AUTH_PERMISSION)
                    else -> it.printStackTrace()
                }
            }).apply { compositeDisposable.add(this) }
    }

    // todo: move method in view
    private fun setCalendars(calendars: List<CalendarListEntry>) {
        layout_calendars.removeAllViews()
        calendars.forEach {
            layout_calendars.addView(createCalendarButton(it))
        }
    }

    private fun createCalendarButton(calendar: CalendarListEntry): Button {
        val button = Button(requireContext())
        button.text = calendar.summary
        button.setOnClickListener {
            moveToCalendarFragment(calendar.id)
        }
        return button
    }

    private fun moveToCalendarFragment(calendarId: String) {
        val action =
            CalendarSelectFragmentDirections.actionDestAuthToDestMonths()
        findNavController().navigate(action)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_AUTH_PERMISSION && resultCode == Activity.RESULT_OK) {
            getCalendars()
        }
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}
