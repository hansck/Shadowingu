package com.hansck.shadowingu.screen.leaderboard


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.adapter.LeaderboardAdapter
import com.hansck.shadowingu.presentation.presenter.LeaderboardPresenter
import com.hansck.shadowingu.presentation.presenter.LeaderboardPresenter.LeaderboardView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_leaderboard.*

/**
 * A simple [Fragment] subclass.
 *
 */
class LeaderboardFragment : BaseFragment(), LeaderboardPresenter.LeaderboardView {

    private lateinit var model: LeaderboardViewModel
    private lateinit var presenter: LeaderboardPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        this.model = LeaderboardViewModel(activity!!)
        this.presenter = LeaderboardPresenterImpl(this)
        doRetrieveModel().setCurrentUser()
        presenter.presentState(UPDATE_USER)
    }

    override fun showState(viewState: LeaderboardPresenter.LeaderboardView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_LEADERBOARD -> showLeaderboard()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): LeaderboardViewModel = this.model

    private fun showLeaderboard() {
        doRetrieveModel().setLeaderboard()
        leaderboardList.setHasFixedSize(true)
        leaderboardList.layoutManager = LinearLayoutManager(activity)
        leaderboardList.adapter = LeaderboardAdapter(doRetrieveModel().users)
        presenter.presentState(IDLE)
    }
}
