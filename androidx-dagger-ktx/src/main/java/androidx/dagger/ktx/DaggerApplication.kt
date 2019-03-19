package androidx.dagger.ktx

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class DaggerApplication: DaggerApplication(), HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}