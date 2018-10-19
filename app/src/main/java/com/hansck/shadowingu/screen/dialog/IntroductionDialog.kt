package com.hansck.shadowingu.screen.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.widget.Button
import com.hansck.shadowingu.R

/**
 * Created by Hans CK on 06-Jul-18.
 */
class IntroductionDialog : DialogFragment() {

	@SuppressLint("InflateParams")
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val inflater = activity!!.layoutInflater
		val view = inflater.inflate(R.layout.dialog_introduction, null)

		val btnDismiss = view.findViewById(R.id.btnDismiss) as Button
		btnDismiss.setOnClickListener {
			dismiss()
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
}