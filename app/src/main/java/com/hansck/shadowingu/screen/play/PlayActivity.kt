package com.hansck.shadowingu.screen.play

import android.os.Bundle
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayPresenter.PlayView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity

class PlayActivity : BaseActivity(), PlayPresenter.PlayView {

    private lateinit var model: PlayViewModel
    private lateinit var presenter: PlayPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        init()
        presenter.presentState(LOAD_WORDS)
    }

    private fun init() {
        this.model = PlayViewModel(this)
        this.presenter = PlayPresenterImpl(this)
    }

    override fun showState(viewState: PlayPresenter.PlayView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            PLAY -> play()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayViewModel = this.model

    private fun play() {

    }
}
