package com.hansck.shadowingu.screen.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.ProgressBarAnimation


/**
 * Created by Hans CK on 06-Jul-18.
 */
class PlayResultDialog : DialogFragment() {

	@SuppressLint("InflateParams")
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val inflater = activity!!.layoutInflater
		val view = inflater.inflate(R.layout.dialog_win_result, null)
		val model = (activity as PlayActivity).doRetrieveModel()

		val level = view.findViewById(R.id.level) as TextView
		level.text = model.oldLevel.toString()

		val time = view.findViewById(R.id.time) as TextView
		time.text = String.format("%02d:%02d", model.timeElapsed / 60, model.timeElapsed % 60)

		val score = view.findViewById(R.id.score) as TextView
		score.text = String.format("%2d/%02d", model.getScore(), Constants.General.WORDS_PER_LEVEL)

		val scoreRemark = view.findViewById(R.id.scoreRemark) as TextView
		if (model.isScoreRecord) {
			scoreRemark.text = getString(R.string.new_record)
			scoreRemark.setTextColor(ContextCompat.getColor(activity!!, R.color.color_accent))
		} else {
			scoreRemark.text = getString(R.string.not_bad_score)
			scoreRemark.setTextColor(ContextCompat.getColor(activity!!, R.color.ic_cancel))
		}

		val timeRemark = view.findViewById(R.id.timeRemark) as TextView
		if (model.isNewTimeRecord) {
			timeRemark.text = getString(R.string.new_record)
			timeRemark.setTextColor(ContextCompat.getColor(activity!!, R.color.color_accent))
		} else {
			timeRemark.text = getString(R.string.not_bad_time)
			timeRemark.setTextColor(ContextCompat.getColor(activity!!, R.color.ic_cancel))
		}

		val progressBar = view.findViewById(R.id.progressBar) as ProgressBar
		try {
			progressBar.max = model.levels[model.oldLevel - 1].exp

			if (model.isLevelUp) {
				animateProgressBar(progressBar, model.oldExp.toFloat(), model.levels[model.oldLevel - 1].exp.toFloat())
				Handler().postDelayed({
					level.text = model.user.level.toString()
					Handler().postDelayed({
						progressBar.max = model.levels[model.user.level - 1].exp
						animateProgressBar(progressBar, 0F, model.user.exp.toFloat())
					}, 1000)
				}, 1000)
			} else {
				animateProgressBar(progressBar, model.oldExp.toFloat(), model.user.exp.toFloat())
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}

		val btnContinue = view.findViewById(R.id.btnContinue) as ImageButton
		btnContinue.setOnClickListener {
			dismiss()
			Common.instance.stopAudio()
			(activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.BACK_TO_HOME)
		}

		val btnReplay = view.findViewById(R.id.btnReplay) as ImageButton
		btnReplay.setOnClickListener {
			dismiss()
			Common.instance.stopAudio()
			(activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.RESET_PLAY)
		}

		val builder = AlertDialog.Builder(activity!!)
		val dialog = builder.setView(view)
				.setOnKeyListener { _, keyCode, _keyEvent ->
					if (keyCode == KeyEvent.KEYCODE_BACK && _keyEvent.action == KeyEvent.ACTION_UP) {
						activity!!.finish()
					}
					false
				}
				.create()
		dialog.setCancelable(false)
		dialog.setCanceledOnTouchOutside(false)
		return dialog
	}

	private fun animateProgressBar(progressBar: ProgressBar, from: Float, to: Float) {
		val anim = ProgressBarAnimation(progressBar, from, to)
		anim.duration = 1000
		progressBar.startAnimation(anim)
	}
}