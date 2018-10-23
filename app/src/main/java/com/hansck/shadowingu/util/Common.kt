package com.hansck.shadowingu.util

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.ImageView

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

	fun checkAudioDuration(activity: Activity, uri: Uri): Boolean {
		return try {
			mPlayer = MediaPlayer.create(activity, uri)
			mPlayer.duration in 251..99999
		} catch (e: Exception) {
			true
		}
	}

	fun playAudio(activity: Activity, audio: String) {
		try {
			mPlayer = MediaPlayer.create(activity, getResourceId(activity, "raw", audio))
			mPlayer.setOnCompletionListener { mp -> mp.release() }
			mPlayer.start()
		} catch (e: Exception) {
			e.stackTrace
		}
	}

	fun stopAudio() {
		try {
			mPlayer.stop()
		} catch (e: Exception) {
			e.stackTrace
		}
	}
	//endregion
}