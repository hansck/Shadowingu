package com.hansck.shadowingu.screen.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.HomePresenter
import com.hansck.shadowingu.presentation.presenter.HomePresenter.HomeView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), HomePresenter.HomeView {

    private lateinit var presenter: HomePresenterImpl
    private lateinit var model: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(LOAD_STAGES)
    }

    private fun init() {
        this.model = HomeViewModel(activity)
        this.presenter = HomePresenterImpl(this)
    }

    override fun showState(viewState: HomePresenter.HomeView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_STAGES -> showStages()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): HomeViewModel = this.model

    private fun showStages() {

    }
}
