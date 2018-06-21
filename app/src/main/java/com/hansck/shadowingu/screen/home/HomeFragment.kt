package com.hansck.shadowingu.screen.home


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.util.Manager
import com.hansck.shadowingu.presentation.adapter.BadgesIconAdapter
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.presentation.adapter.StagesAdapter
import com.hansck.shadowingu.presentation.customview.OnBadgeSelected
import com.hansck.shadowingu.presentation.customview.OnListItemSelected
import com.hansck.shadowingu.presentation.presenter.HomePresenter
import com.hansck.shadowingu.presentation.presenter.HomePresenter.HomeView.ViewState.*
import com.hansck.shadowingu.screen.achievement.AchievementActivity
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.ListDivider
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), HomePresenter.HomeView, OnListItemSelected, OnBadgeSelected {

    private lateinit var presenter: HomePresenterImpl
    private lateinit var model: HomeViewModel
    private lateinit var adapter: StagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(SHOW_ITEMS)
    }

    private fun init() {
        this.model = HomeViewModel(activity)
        this.presenter = HomePresenterImpl(this)

        profileName.text = Manager.instance.user.name
        title.text = Manager.instance.getActiveTitle().name
        exp.text = Manager.instance.user.exp.toString()
//        ImageUtil.instance.setImage(activity!!, Manager.user.image, image)
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

    override fun onClick(position: Int) {
        val intent = Intent(activity, PlayActivity::class.java)
        startActivity(intent)
    }

    override fun onBadgeSelected(position: Int) {
        val intent = Intent(activity, AchievementActivity::class.java)
        startActivity(intent)
    }

    private fun showItems() {
        // Set Stage List
        doRetrieveModel().setStages()
        stageList.setHasFixedSize(true)
        stageList.layoutManager = LinearLayoutManager(context)
        stageList.addItemDecoration(ListDivider(activity!!, R.drawable.bg_divider_full))
        adapter = StagesAdapter(doRetrieveModel().stages, this)

        // show the data
        val dummy = arrayOfNulls<SectionListAdapter.Section>(doRetrieveModel().categories.size)
        val mSectionedAdapter = SectionListAdapter(activity, R.layout.item_section, R.id.section_text, stageList, adapter)
        mSectionedAdapter.setSections(doRetrieveModel().categories.toArray(dummy))
        stageList.adapter = adapter
        stageList.adapter = mSectionedAdapter

        // Set Badge List
        doRetrieveModel().setBadges()
        badgesList.setHasFixedSize(true)
        badgesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        badgesList.adapter = BadgesIconAdapter(doRetrieveModel().badges, this)
    }
}
