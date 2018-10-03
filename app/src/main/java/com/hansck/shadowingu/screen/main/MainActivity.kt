package com.hansck.shadowingu.screen.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.auth.api.Auth
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.MainPresenter
import com.hansck.shadowingu.presentation.presenter.MainPresenter.MainView.ViewState.*
import com.hansck.shadowingu.screen.TestActivity
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.login.LoginActivity
import com.hansck.shadowingu.screen.tab.TabFragment
import com.hansck.shadowingu.util.AuthManager


class MainActivity : BaseActivity(), MainPresenter.MainView {

	private lateinit var model: MainViewModel
	private lateinit var presenter: MainPresenter
	lateinit var fm: FragmentManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		init()
		presenter.presentState(LOAD_USER)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == R.id.action_sign_out) {
			onSignOut()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	private fun init() {
		this.model = MainViewModel(this)
		this.presenter = MainPresenterImpl(this)
		fm = supportFragmentManager
	}

	override fun showState(viewState: MainPresenter.MainView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			LOAD_TAB -> loadTabFragment()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): MainViewModel = this.model

	private fun loadTabFragment() {
//        goToTest()
		val bundle = Bundle()
		val fragment: BaseFragment = TabFragment()
		fragment.arguments = bundle
		navigateTo(fm, fragment)
		presenter.presentState(IDLE)
	}

	private fun onSignOut() {
		AuthManager.instance.auth.signOut()
		Auth.GoogleSignInApi.signOut(AuthManager.instance.googleApiClient).setResultCallback {
			goToLogin()
		}
	}

	private fun goToLogin() {
		val intent = Intent(this, LoginActivity::class.java)
		startActivity(intent)
		finish()
	}

	private fun goToTest() {
		val intent = Intent(this, TestActivity::class.java)
		startActivity(intent)
		finish()
	}
}
