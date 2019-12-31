package com.chazo.personal.two.cleanarchitecturestudy.di.auth

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideGoogleAccountCredential(context: Context): GoogleAccountCredential =
            GoogleAccountCredential
                .usingOAuth2(context, listOf(CalendarScopes.CALENDAR))
                .setBackOff(ExponentialBackOff())
    }
}