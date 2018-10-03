package com.hansck.shadowingu.util

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar


/**
 * Created by Hans CK on 09-Jul-18.
 */
open class ProgressBarAnimation(val progressBar: ProgressBar, val from: Float, val to: Float) : Animation() {

	override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
		super.applyTransformation(interpolatedTime, t)
		val value = from + (to - from) * interpolatedTime
		progressBar.progress = value.toInt()
	}
}