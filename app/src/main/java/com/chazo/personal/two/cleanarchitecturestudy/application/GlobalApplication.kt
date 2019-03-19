package com.chazo.personal.two.cleanarchitecturestudy.application

import com.chazo.personal.two.cleanarchitecturestudy.application.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class GlobalApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().application(this).build()
}