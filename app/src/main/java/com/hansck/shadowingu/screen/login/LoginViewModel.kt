package com.hansck.shadowingu.screen.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.util.AuthManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LoginViewModel(var context: Context?) {

    lateinit var account: GoogleSignInAccount

    fun setAcct() {
        account = AuthManager.instance.account
    }

    fun getUser(): LeaderboardUser {
        return LeaderboardUser(account.email!!, account.displayName!!, 0, account.photoUrl.toString(), Badge.populateData().toList())
    }
}