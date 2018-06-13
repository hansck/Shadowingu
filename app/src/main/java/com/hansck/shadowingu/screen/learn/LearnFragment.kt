package com.hansck.shadowingu.screen.learn


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.LearnPresenter
import com.hansck.shadowingu.presentation.presenter.LearnPresenter.LearnView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class LearnFragment : BaseFragment(), LearnPresenter.LearnView {

    private lateinit var presenter: LearnPresenterImpl
    private lateinit var model: LearnViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(SHOW_STAGES)
    }

    private fun init() {
        this.model = LearnViewModel(activity)
        this.presenter = LearnPresenterImpl(this)
    }

    override fun showState(viewState: LearnPresenter.LearnView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_STAGES -> showStages()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): LearnViewModel = this.model

    private fun showStages() {

    }
}
