package com.chazo.personal.two.cleanarchitecturestudy.application

import androidx.dagger.ktx.DaggerApplication
import com.chazo.personal.two.cleanarchitecturestudy.application.di.DaggerApplicationComponent
import dagger.android.AndroidInjector

class GlobalApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().application(this).build()
}