package com.chazo.personal.two.cleanarchitecturestudy.di.data

import android.content.Context
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.GoogleCalendarDataSource
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.GoogleCalendarRepository
import com.chazo.personal.two.cleanarchitecturestudy.data.google_calender.remote.GoogleCalendarRemoteDataSource
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
abstract class GoogleCalendarDataModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideHttpTransport(): HttpTransport =
            AndroidHttp.newCompatibleTransport()

        @JvmStatic
        @Singleton
        @Provides
        fun provideJacksonFactory(): JacksonFactory =
            JacksonFactory.getDefaultInstance()

        @JvmStatic
        @Singleton
        @Provides
        fun provideGoogleCalendarRemoteDataSource(
            googleAccountCredential: GoogleAccountCredential,
            transport: HttpTransport,
            jacksonFactory: JacksonFactory
        ): GoogleCalendarRemoteDataSource =
            GoogleCalendarRemoteDataSource(googleAccountCredential, transport, jacksonFactory)

    }

    @Singleton
    @Binds
    abstract fun provideGoogleCalendarRepository(googleCalendarRepository: GoogleCalendarRepository): GoogleCalendarDataSource
}
