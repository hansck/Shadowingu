package com.hansck.shadowingu.screen.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Topic
import com.hansck.shadowingu.presentation.adapter.BadgesIconAdapter
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.presentation.adapter.TopicAdapter
import com.hansck.shadowingu.presentation.customview.OnBadgeSelected
import com.hansck.shadowingu.presentation.customview.OnStageSelected
import com.hansck.shadowingu.presentation.presenter.HomePresenter
import com.hansck.shadowingu.presentation.presenter.HomePresenter.HomeView.ViewState.*
import com.hansck.shadowingu.screen.achievement.AchievementActivity
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.Common
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), HomePresenter.HomeView, OnStageSelected, OnBadgeSelected {

	private lateinit var model: HomeViewModel
	private lateinit var presenter: HomePresenter
	private var adapter: TopicAdapter? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()
		presenter.presentState(LOAD_BADGES)
	}

	override fun onResume() {
		super.onResume()
		adapter?.notifyDataSetChanged()
		badgesList?.adapter?.notifyDataSetChanged()
		showProfile()
	}

	private fun init() {
		this.model = HomeViewModel(activity)
		this.presenter = HomePresenterImpl(this)

		doRetrieveModel().setData()
	}

	private fun showProfile() {
		profileName.text = doRetrieveModel().user.name
		exp.text = doRetrieveModel().user.exp.toString()
		title.text = doRetrieveModel().getActiveTitle().name
		Common.instance.setImageByName(activity!!, doRetrieveModel().user.image, picture)
	}

	override fun showState(viewState: HomePresenter.HomeView.ViewState) {
		when (viewState) {
			IDLE -> showProgress(false)
			LOADING -> showProgress(true)
			SHOW_ITEMS -> showItems()
			ERROR -> showError(null, getString(R.string.failed_request_general))
		}
	}

	override fun doRetrieveModel(): HomeViewModel = this.model

	override fun onStageSelected(topic: Topic) {
		val intent = Intent(activity, PlayActivity::class.java)
		intent.putExtra("idTopic", topic.idTopic)
		startActivity(intent)
	}

	override fun onBadgeSelected(position: Int) {
		val intent = Intent(activity, AchievementActivity::class.java)
		startActivity(intent)
	}

	private fun showItems() {
		// Set Topic List
		doRetrieveModel().setStagesAndBadges()
		stageList.setHasFixedSize(true)
		stageList.layoutManager = LinearLayoutManager(context)
		adapter = TopicAdapter(doRetrieveModel().topics, false, this)

		// show the data
		val dummy = arrayOfNulls<SectionListAdapter.Section>(doRetrieveModel().categories.size)
		val mSectionedAdapter = SectionListAdapter(activity, R.layout.item_section, R.id.section_text, stageList, adapter)
		mSectionedAdapter.setSections(doRetrieveModel().categories.toArray(dummy))
		stageList.adapter = adapter
		stageList.adapter = mSectionedAdapter

		// Set Badge List
		badgesList.setHasFixedSize(true)
		badgesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		badgesList.adapter = BadgesIconAdapter(doRetrieveModel().badges, this)
	}
}
