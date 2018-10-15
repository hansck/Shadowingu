package com.hansck.shadowingu.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.hansck.shadowingu.R

/**
 * Created by Hans CK on 1-Nov-17.
 */
class Common private constructor() {

	lateinit var mPlayer: MediaPlayer

	companion object {
		val instance = Common()
	}

	//region Public methods
	fun showToast(activity: Activity, text: String, duration: Int) {
		try {
			val snackbar = Snackbar.make(activity.currentFocus!!, text, duration)
			val tv = snackbar.view.findViewById<View>(R.id.snackbar_text) as TextView
			tv.maxLines = 5
			snackbar.show()
		} catch (e: Exception) {
			val view = activity.window.decorView.findViewById<View>(R.id.content)
			if (view != null) {
				Snackbar.make(view, text, duration).show()
			} else {
//                Crashlytics.logException(e)
			}
		}
	}

	fun getResourceId(context: Context, type: String, identifier: String): Int {
		return context.resources.getIdentifier(identifier, type, context.packageName)
	}

	fun setImageByName(context: Context, imageName: String, view: ImageView) {
		view.setImageResource(getResourceId(context, "drawable", imageName))
	}

	fun playAudio(activity: Activity, audio: String) {
		mPlayer = MediaPlayer.create(activity, getResourceId(activity, "raw", audio))
		mPlayer.setOnCompletionListener { mp -> mp.release() }
		mPlayer.start()
	}

	fun stopAudio() {
		mPlayer.stop()
	}

	fun hideSoftKeyboard(activity: Activity) {
		try {
			val i = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			if (activity.currentFocus != null) {
				i.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager
						.HIDE_NOT_ALWAYS)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun verifyPermission(grantResults: IntArray): Boolean {
		if (grantResults.size < 1) {
			return false
		}
		for (result in grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				return false
			}
		}
		return true
	}
	//endregion
}