package com.hansck.shadowingu.screen.play

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.widget.Chronometer
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.adapter.HeartAdapter
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayPresenter.PlayView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.dialog.GameOverDialog
import com.hansck.shadowingu.screen.dialog.PlayResultDialog
import com.hansck.shadowingu.screen.playword.PlayWordFragment
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : BaseActivity(), PlayPresenter.PlayView {

    private lateinit var model: PlayViewModel
    lateinit var presenter: PlayPresenter
    lateinit var fm: FragmentManager
    private var elapsedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_play)
        init()
    }

    private fun init() {
        this.model = PlayViewModel(this)
        this.presenter = PlayPresenterImpl(this)
        fm = supportFragmentManager

        doRetrieveModel().setData(intent.extras.getInt("idStage"))

        heartList.setHasFixedSize(true)
        heartList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        heartList.adapter = HeartAdapter(doRetrieveModel().hearts)

        setTimer()

        presenter.presentState(SHOW_WORD_SCREEN)
    }

    private fun setTimer() {
        timer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            elapsedTime += 1000
        }
        timer.base = SystemClock.elapsedRealtime();
        timer.start()
    }

    override fun showState(viewState: PlayPresenter.PlayView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORD_SCREEN -> showFragment()
            SHOW_PLAY_RESULT -> showPlayResult()
            SHOW_GAME_OVER -> showGameOver()
            BACK_TO_HOME -> backToHome()
            SHOW_CORRECT -> presenter.presentState(SHOW_PLAY_RESULT)
            SHOW_WRONG -> displayWrongAnswer()
            RESET_PLAY -> {
                resetPlay()
                setTimer()
            }
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayViewModel = this.model

    private fun showFragment() {
        if (doRetrieveModel().count > 0) {
            val fragment: BaseFragment = PlayWordFragment()
            val bundle = Bundle()
            bundle.putInt("idWord", doRetrieveModel().currentWordId)
            fragment.arguments = bundle
            navigateTo(fm, fragment)
            doRetrieveModel().setCount()
        } else {
            timer.stop()
            doRetrieveModel().calculatePlayResult()
            presenter.presentState(UPDATE_USER)
        }
        presenter.presentState(IDLE)
    }

    private fun showPlayResult() {
        val playResultDialog = PlayResultDialog()
        playResultDialog.show(fm, "playResult")
    }

    private fun showGameOver() {
        val gameOverDialog = GameOverDialog()
        gameOverDialog.show(fm, "gameOver")
    }

    private fun backToHome() {
        onBackPressed()
    }

    private fun resetPlay() {
        doRetrieveModel().resetPlay()
        heartList.adapter.notifyDataSetChanged()
        presenter.presentState(SHOW_WORD_SCREEN)
    }

    private fun displayWrongAnswer() {
        doRetrieveModel().reduceHeart()
        heartList.adapter.notifyDataSetChanged()
        if (doRetrieveModel().numOfHearts == 0) {
            if (doRetrieveModel().checkGameOverBadge()) {
                presenter.presentState(UPDATE_BADGE)
            } else {
                presenter.presentState(SHOW_GAME_OVER)
            }
        }
    }
}
