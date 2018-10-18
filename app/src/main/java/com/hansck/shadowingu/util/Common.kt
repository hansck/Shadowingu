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
	//endregion
}