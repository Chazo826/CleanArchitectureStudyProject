package com.chazo.personal.two.cleanarchitecturestudy.controller.calendarview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.GoogleCalendarRepository
import com.google.api.services.calendar.model.Event
import dagger.android.support.DaggerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_calendar.*
import javax.inject.Inject

class CalendarFragment : DaggerFragment() {

    private lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var googleCalendarRepository: GoogleCalendarRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        super.onCreateView(inflater, container, savedInstanceState)
            ?: inflater.inflate(R.layout.fragment_calendar, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            CalendarFragmentArgs.fromBundle(it).let { args ->
                if(args.calendarId != getString(R.string.calendarId_default)) {
                    // 3. presenter 인터페이스 함수를 호출한다.
                    getEvents(args.calendarId)
                }
            }
        }
    }

    /**
     * 1. view 인터페이스를 정의한다.
     * 2. presenter 인터페이스를 정의한다.
     */

    // 4. callback 함수를 정의한다.
    public fun setEvents(event: List<Event>?) {
        event?.let {
            text_calendar_data.text = createEventsText(it)
        }
    }

    // todo: move method in presenter
    // compositeDisposable
    private fun getEvents(calendarId: String) {
        googleCalendarRepository.getEvents(calendarId)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress_loading?.visibility = View.GONE }
            .subscribe({
                // 4번 callback 함수를 호출한다.
                setEvents(it)
            }, {
                it.printStackTrace()
            }).apply { compositeDisposable.add(this) }
    }

    private fun createEventsText(events: List<Event>): String {
        val size = events.size
        return events.foldIndexed("") { index, acc, event ->
            acc + "date=${event.start.date} summary=${event.summary}" + if (index == size - 1) "" else "\n"
        }
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }
}
