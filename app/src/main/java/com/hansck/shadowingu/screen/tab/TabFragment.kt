package com.hansck.shadowingu.screen.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.adapter.TabAdapter
import com.hansck.shadowingu.presentation.presenter.TabPresenter
import com.hansck.shadowingu.presentation.presenter.TabPresenter.TabView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.home.HomeFragment
import com.hansck.shadowingu.screen.leaderboard.LeaderboardFragment
import com.hansck.shadowingu.screen.learn.LearnFragment
import com.hansck.shadowingu.screen.shop.ShopFragment
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : BaseFragment(), TabPresenter.TabView {

    private lateinit var model: TabViewModel
    private lateinit var presenter: TabPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.model = TabViewModel(activity)
        this.presenter = TabPresenterImpl(this)
        presenter.presentState(SHOW_TAB)
    }

    override fun showState(viewState: TabPresenter.TabView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_TAB -> showTab()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): TabViewModel = this.model

    private fun showTab() {
        tab.setupWithViewPager(viewPager)
        val adapter = TabAdapter(childFragmentManager)
        adapter.addFragment(HomeFragment(), getString(R.string.home))
        adapter.addFragment(LearnFragment(), getString(R.string.learn))
        adapter.addFragment(LeaderboardFragment(), getString(R.string.leaderbaoard))
        adapter.addFragment(ShopFragment(), getString(R.string.shop))
        viewPager.adapter = adapter
    }
}
