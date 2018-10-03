package com.hansck.shadowingu.screen.achievement


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.adapter.BadgesAdapter
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter
import com.hansck.shadowingu.presentation.presenter.AchievementPresenter.AchievementView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_achievements.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AchievementActivity : BaseActivity(), AchievementPresenter.AchievementView {

	private lateinit var model: AchievementViewModel
	private lateinit var presenter: AchievementPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_achievements)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		init()
		presenter.presentState(SHOW_ACHIEVEMENT)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			android.R.id.home -> {
				finish()
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	private fun init() {
		this.model = AchievementViewModel(this)
		this.presenter = AchievementPresenterImpl(this)
	}

	override fun showState(viewState: AchievementPresenter.AchievementView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			SHOW_ACHIEVEMENT -> showBadges()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): AchievementViewModel = this.model

	fun showBadges() {
		doRetrieveModel().setBadges()
		badgesList.setHasFixedSize(true)
		badgesList.layoutManager = LinearLayoutManager(this)
		badgesList.adapter = BadgesAdapter(doRetrieveModel().badges)
	}
}
