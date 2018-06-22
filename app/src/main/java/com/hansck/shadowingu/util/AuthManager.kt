package com.hansck.shadowingu.util

import android.content.SharedPreferences
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hansck.shadowingu.R

/**
 * Created by Hans CK on 21-Jun-18.
 */
class AuthManager : GoogleApiClient.OnConnectionFailedListener {

    companion object {
        val instance = AuthManager()
    }

    lateinit var googleApiClient: GoogleApiClient
    lateinit var account: GoogleSignInAccount
    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var keyStore: SharedPreferences

    fun initAuth(activity: FragmentActivity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleApiClient = GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        if (!googleApiClient.isConnected) {
            googleApiClient.connect()
        }

        auth = FirebaseAuth.getInstance()
    }

    fun clearKeyStore() {
        keyStore.edit().clear().apply()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e("AUTHENTICATION", "Connection Failed!")
    }
}