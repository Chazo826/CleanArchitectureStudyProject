package com.chazo.personal.two.cleanarchitecturestudy.controller.auth

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.chazo.personal.two.cleanarchitecturestudy.R
import com.chazo.personal.two.cleanarchitecturestudy.constant.RC_ACCOUNT_PICKER
import com.chazo.personal.two.cleanarchitecturestudy.constant.RP_GET_ACCOUNTS
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class AuthFragment : DaggerFragment() {

    @Inject
    lateinit var googleAccountCredential: GoogleAccountCredential

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_sign_in.setOnClickListener { selectAccount() }
    }

    private fun selectAccount() {
        val accountPermission = Manifest.permission.GET_ACCOUNTS
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                accountPermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showAccountPicker()
        } else {
            requestPermissions(arrayOf(accountPermission), RP_GET_ACCOUNTS)
        }
    }

    private fun showAccountPicker() {
        startActivityForResult(googleAccountCredential.newChooseAccountIntent(), RC_ACCOUNT_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_ACCOUNT_PICKER && resultCode == Activity.RESULT_OK) {
            data?.let {
                val accountName = it.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                accountName?.let {
                    googleAccountCredential.selectedAccountName = it
                    moveToCalendarSelectFragment()
                } ?: run {
                    Toast.makeText(requireContext(), "선택된 계정이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun moveToCalendarSelectFragment() {
        AuthFragmentDirections.actionDestAuthToDestCalendarSelect().apply {
            findNavController().navigate(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RP_GET_ACCOUNTS -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showAccountPicker()
                } else {
                    Toast.makeText(requireContext(), "정상적인 작동을 위해 앱 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
