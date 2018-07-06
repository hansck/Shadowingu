package com.hansck.shadowingu.screen.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.widget.ImageButton
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.screen.play.PlayActivity

/**
 * Created by Hans CK on 06-Jul-18.
 */
class GameOverDialog : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_game_over, null)
        val btnContinue = view.findViewById(R.id.btnContinue) as ImageButton
        btnContinue.setOnClickListener {
            (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.BACK_TO_HOME)
            this.dismiss()
        }

        val btnReplay = view.findViewById(R.id.btnReplay) as ImageButton
        btnReplay.setOnClickListener {
            (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.RESET_PLAY)
            this.dismiss()
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