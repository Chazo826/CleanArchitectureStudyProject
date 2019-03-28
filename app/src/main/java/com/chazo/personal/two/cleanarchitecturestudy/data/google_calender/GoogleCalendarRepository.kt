package com.chazo.personal.two.cleanarchitecturestudy.data.google_calender

import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.remote.GoogleCalendarRemoteDataSource
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.Event
import io.reactivex.Single
import javax.inject.Inject

class GoogleCalendarRepository @Inject constructor(
    private val remote: GoogleCalendarRemoteDataSource
) : GoogleCalendarDataSource {

    //코틀린 답게 축약
    override fun getCalendarList(): Single<CalendarList> =
        remote.getCalendarList()

    override fun getEvents(calendarId: String): Single<List<Event>> =
        remote.getEvents(calendarId)
}