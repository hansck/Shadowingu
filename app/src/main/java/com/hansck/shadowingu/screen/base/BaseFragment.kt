package com.hansck.shadowingu.screen.base

import android.support.v4.app.Fragment
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.base.BaseView


open class BaseFragment : Fragment(), BaseView {

	override fun showProgress(flag: Boolean) {
		(activity as BaseActivity).showProgress(flag)
	}

	override fun showToast(message: String) {
		(activity as BaseActivity).showToast(message)
	}

	override fun showError(title: String?, message: String) {
		(activity as BaseActivity).showError(title, message)
	}

	protected fun navigateFragment(frame: Int, fragment: Fragment) {
		val fm = activity!!.supportFragmentManager
		val ft = fm.beginTransaction()
		ft.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right)
		ft.replace(frame, fragment)
		ft.addToBackStack(null)
		ft.commit()
	}

	protected fun navigateFragmentWithoutAnimation(frame: Int, fragment: Fragment) {
		val fm = activity!!.supportFragmentManager
		val ft = fm.beginTransaction()
		ft.replace(frame, fragment)
		ft.addToBackStack(null)
		ft.commit()
	}

	protected fun navigateFragmentWithBackstack(frame: Int, fragment: Fragment) {
		val fm = activity!!.supportFragmentManager
		fm.popBackStackImmediate()
		val ft = fm.beginTransaction()
		ft.setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right)
		ft.replace(frame, fragment)
		ft.addToBackStack(null)
		ft.commit()
	}
}
