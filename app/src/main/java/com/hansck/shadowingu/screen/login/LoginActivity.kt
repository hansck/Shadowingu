package com.hansck.shadowingu.screen.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.presentation.presenter.LoginPresenter
import com.hansck.shadowingu.presentation.presenter.LoginPresenter.LoginView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.screen.main.MainActivity
import com.hansck.shadowingu.util.AuthManager
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.FirebaseDB
import com.hansck.shadowingu.util.PersistentManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginPresenter.LoginView {

    private lateinit var model: LoginViewModel
    private lateinit var presenter: LoginPresenter
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AuthManager.instance.initAuth(this)
        val preference = getSharedPreferences("preference", Context.MODE_PRIVATE)
        PersistentManager.instance.keyStore = preference

        init()
        presenter.presentState(IDLE)
    }

    private fun init() {
        this.model = LoginViewModel(this)
        this.presenter = LoginPresenterImpl(this)

        btnSignIn.setOnClickListener { presenter.presentState(ATTEMPT_LOGIN) }

        listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                AuthManager.instance.firebaseUser = firebaseAuth.currentUser!!
                presenter.presentState(ENTER)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        AuthManager.instance.auth.addAuthStateListener(listener)
    }

    public override fun onStop() {
        super.onStop()
        AuthManager.instance.auth.removeAuthStateListener(listener)
    }

    override fun showState(viewState: LoginPresenter.LoginView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            ATTEMPT_LOGIN -> attemptLogin()
            ENTER -> {
                goToMain()
            }
            ERROR -> {
                showError(null, getString(R.string.failed_request_general))
                presenter.presentState(IDLE)
            }
        }
    }

    override fun showProgress(flag: Boolean) {
        if (flag) {
            btnSignIn.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            btnSignIn.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun doRetrieveModel(): LoginViewModel = this.model

    fun attemptLogin() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(AuthManager.instance.googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                presenter.presentState(ERROR)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        AuthManager.instance.auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        presenter.presentState(ERROR)
                    } else {
                        AuthManager.instance.account = acct
                        doRetrieveModel().setAcct()
                        checkUser()
                    }
                }
    }

    fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        presenter.presentState(IDLE)
        finish()
    }

    private fun uploadContent() {
        val user = doRetrieveModel().getLeaderboardUser()
        val id = FirebaseDB.instance.getKey(Constants.Database.USER)!!
        FirebaseDB.instance.getDbReference(Constants.Database.USER).child(id).setValue(user)
        PersistentManager.instance.setUserKey(id)
        presenter.presentState(UPDATE_USER)
    }

    private fun checkUser() {
        val ref = FirebaseDB.instance.getDbReference(Constants.Database.USER)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isExist = false
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue(LeaderboardUser::class.java)
                    if (user?.email == doRetrieveModel().account.email) {
                        isExist = true
                    }
                }
                if (!isExist) uploadContent()
                ref.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.presentState(ERROR)
                ref.removeEventListener(this)
            }
        })
    }
}
