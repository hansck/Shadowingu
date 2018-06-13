package com.hansck.shadowingu.screen.achievement


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter.AchievementView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class AchievementFragment : BaseFragment(), AchievementPresenter.AchievementView {

    private lateinit var presenter: AchievementPresenterImpl
    private lateinit var model: AchievementViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(LOAD_ACHIEVEMENT)
    }

    private fun init() {
        this.model = AchievementViewModel(activity)
        this.presenter = AchievementPresenterImpl(this)
    }

    override fun showState(viewState: AchievementPresenter.AchievementView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_ACHIEVEMENT -> showAchievement()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): AchievementViewModel = this.model

    private fun showAchievement() {

    }
}
