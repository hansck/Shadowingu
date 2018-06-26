package com.hansck.shadowingu.screen.play

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayPresenter.PlayView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.playword.PlayWordFragment

class PlayActivity : BaseActivity(), PlayPresenter.PlayView {

    private lateinit var model: PlayViewModel
    lateinit var presenter: PlayPresenter
    lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        init()
    }

    private fun init() {
        this.model = PlayViewModel(this)
        this.presenter = PlayPresenterImpl(this)
        fm = supportFragmentManager
        doRetrieveModel().setWords(intent.extras.getInt("idStage"))
        presenter.presentState(SHOW_WORD_SCREEN)
    }

    override fun showState(viewState: PlayPresenter.PlayView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORD_SCREEN -> showFragment()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayViewModel = this.model

    private fun showFragment() {
        val fragment: BaseFragment = PlayWordFragment()
        val bundle = Bundle()
        bundle.putInt("idWord", doRetrieveModel().currentWordId)
        fragment.arguments = bundle
        navigateTo(fm, fragment)
        doRetrieveModel().currentWordId++
        presenter.presentState(IDLE)
    }
}
