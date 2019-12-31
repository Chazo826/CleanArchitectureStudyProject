package com.chazo.personal.two.cleanarchitecturestudy.di.application

import android.app.Application
import com.chazo.personal.two.cleanarchitecturestudy.GlobalApplication
import com.chazo.personal.two.cleanarchitecturestudy.di.data.GoogleCalendarDataModule
import com.chazo.personal.two.cleanarchitecturestudy.di.activity.ActivityBindingModule
import com.chazo.personal.two.cleanarchitecturestudy.di.auth.AuthModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityBindingModule::class,
        AuthModule::class,
        GoogleCalendarDataModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<GlobalApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}