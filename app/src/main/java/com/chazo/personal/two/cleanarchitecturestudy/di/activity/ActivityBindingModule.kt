package com.chazo.personal.two.cleanarchitecturestudy.di.activity

import com.chazo.personal.two.cleanarchitecturestudy.MainActivity
import com.chazo.personal.two.cleanarchitecturestudy.di.scopes.ActivityScoped
import com.chazo.personal.two.cleanarchitecturestudy.di.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}