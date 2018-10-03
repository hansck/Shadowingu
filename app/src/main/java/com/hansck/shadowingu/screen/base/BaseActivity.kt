package com.hansck.shadowingu.screen.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.App
import com.hansck.shadowingu.presentation.base.BaseView
import com.hansck.shadowingu.util.ConnectivityReceiver
import com.hansck.shadowingu.util.ConnectivityReceiver.Companion.IS_NETWORK_AVAILABLE


open class BaseActivity : AppCompatActivity(), BaseView {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		App.getInstance.connected = isOnline()

		val intentFilter = IntentFilter(ConnectivityReceiver.NETWORK_AVAILABLE_ACTION)
		LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				val isConnected = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false)
				App.getInstance.connected = isConnected
				if (!isConnected)
					showToast(getString(R.string.no_internet_alert))
			}
		}, intentFilter)
	}

	override fun showProgress(flag: Boolean) {
	}

	override fun showToast(message: String) {
		val snackbar = Snackbar.make(findViewById(android.R.id.content),
				message, Snackbar.LENGTH_SHORT)
		val sbView = snackbar.view
		val textView = sbView
				.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
		textView.setTextColor(ContextCompat.getColor(this, R.color.bg_light_1))
		snackbar.show()
	}

	override fun showError(title: String?, message: String) {
		if (message != null) {
			showToast(message)
		} else {
			showToast(getString(R.string.failed_request_general))
		}
	}

	// region Private Methods
	private fun isOnline(): Boolean {
		val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val netInfo = cm.activeNetworkInfo
		return netInfo != null && netInfo.isConnectedOrConnecting
	}

	protected fun navigateTo(fm: FragmentManager, fragment: Fragment) {
		if (!isFinishing) {
			for (i in 0 until fm.backStackEntryCount) {
				fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
			}
			val ft = fm.beginTransaction()
			ft.setCustomAnimations(R.anim.enter_right, R.anim.exit_left)
			ft.replace(R.id.contentFrame, fragment)
			ft.commitAllowingStateLoss()
		}
	}

	protected fun initToolbar(appCompatActivity: AppCompatActivity?, toolbar: Toolbar?,
	                          title: String, isCollapsing: Boolean) {
		if (toolbar == null || appCompatActivity == null) {
			throw IllegalArgumentException("toolbar or appCompatActivity is null")
		}
		setSupportActionBar(toolbar)
		val actionBar = supportActionBar ?: return
		actionBar.setHomeButtonEnabled(true)
		actionBar.setDisplayHomeAsUpEnabled(true)
		actionBar.title = title

		// disable collapsing
		if (!isCollapsing) {
			val p = toolbar.layoutParams as AppBarLayout.LayoutParams
			p.scrollFlags = 0
			toolbar.layoutParams = p
		}
	}
	// endregion
}
