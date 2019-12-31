package com.chazo.personal.two.cleanarchitecturestudy.controller.splash

import android.content.Intent
import android.os.Bundle
import com.chazo.personal.two.cleanarchitecturestudy.MainActivity
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.constant.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import dagger.android.support.DaggerAppCompatActivity

class SplashActivity: DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupAuthSession()
    }

    private fun setupAuthSession() {
        FirebaseAuth.getInstance().currentUser?.let {

            startActivity(Intent(this, MainActivity::class.java))
        } ?: kotlin.run {
            showLoginDisplay()
        }
    }

    private fun showLoginDisplay() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        )
    }
}