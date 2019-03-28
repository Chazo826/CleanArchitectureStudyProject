package com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.remote

import android.util.Log
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.GoogleCalendarDataSource
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.Event
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoogleCalendarRemoteDataSource @Inject constructor(
    googleAccountCredential: GoogleAccountCredential,
    transport: HttpTransport,
    jsonFactory: JsonFactory
) : GoogleCalendarDataSource {

    private val calendar: Calendar = Calendar.Builder(transport, jsonFactory, googleAccountCredential)
        .setApplicationName("Google Calendar Api MVC")
        .build()

    override fun getCalendarList(): Single<CalendarList> =
        Single.fromCallable {
            // test Schedulers.io() 인지 main thread 인지
            Log.d("!!!!", "Thread ${Thread.currentThread()}")
            calendar.CalendarList().list().execute()
        }
            .subscribeOn(Schedulers.io())
            .map {
                Log.d("!!!!", "Thread-2 ${Thread.currentThread()}")
                it
            }


    override fun getEvents(calendarId: String): Single<List<Event>> =
        Single.fromCallable {
            calendar.events()
                .list(calendarId)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute()
        }
            .subscribeOn(Schedulers.io())
            .map { it.items }
}