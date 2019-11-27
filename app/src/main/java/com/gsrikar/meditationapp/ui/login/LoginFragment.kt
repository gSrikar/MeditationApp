package com.gsrikar.meditationapp.ui.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.gsrikar.meditationapp.BuildConfig
import com.gsrikar.meditationapp.R
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    companion object {
        // Log cat tag
        private val TAG = LoginFragment::class.java.simpleName
        // True for debug build and false otherwise
        private val DBG = BuildConfig.DEBUG

        private const val REQUEST_CODE_LOGIN = 3891
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkUserAuthenticated()
    }

    private fun checkUserAuthenticated() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            navigateMain()
        } else {
            showLogin()
        }
    }

    private fun showLogin() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.AnonymousBuilder().build()
                    )
                )
                .build(),
            REQUEST_CODE_LOGIN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN) {
            val response = IdpResponse.fromResultIntent(data)
            if (DBG) Log.d(TAG, "Intent: ${response.toString()}")
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                navigateMain()
            } else {
                if (DBG) Log.d(TAG, "User cancelled the request")
                parseError(response)
            }
        }
    }

    private fun parseError(response: IdpResponse?) {
        when {
            response == null -> {
                // User pressed back button
                showError(R.string.sign_in_cancelled)
            }
            response.error?.errorCode == ErrorCodes.NO_NETWORK -> {
                showError(R.string.no_internet_connection)
            }
            else -> {
                showError(R.string.unknown_error)
                if (DBG) Log.e(TAG, "Sign-in error: ", response.error)
            }
        }
    }

    private fun showError(@StringRes messageId: Int) {
        errorTextView.setText(messageId)
    }

    private fun navigateMain() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToMainFragment()
        )
    }

}
