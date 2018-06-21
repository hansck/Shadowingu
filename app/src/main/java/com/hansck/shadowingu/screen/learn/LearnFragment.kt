package com.hansck.shadowingu.screen.learn


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.presentation.adapter.StagesAdapter
import com.hansck.shadowingu.presentation.customview.OnListItemSelected
import com.hansck.shadowingu.presentation.presenter.LearnPresenter
import com.hansck.shadowingu.presentation.presenter.LearnPresenter.LearnView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.ListDivider
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class LearnFragment : BaseFragment(), LearnPresenter.LearnView, OnListItemSelected {

    private lateinit var presenter: LearnPresenterImpl
    private lateinit var model: LearnViewModel
    private lateinit var adapter: StagesAdapter

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

        profileContainer.visibility = View.GONE
        progressContainer.visibility = View.VISIBLE
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

    override fun onClick(position: Int) {
        val intent = Intent(activity, PlayActivity::class.java)
        startActivity(intent)
    }

    private fun showStages() {
        stageList.setHasFixedSize(true)
        stageList.layoutManager = LinearLayoutManager(context)
        stageList.addItemDecoration(ListDivider(activity!!, R.drawable.bg_divider_full))

        doRetrieveModel().setStages()
        adapter = StagesAdapter(doRetrieveModel().stages, this)

        // show the data
        val dummy = arrayOfNulls<SectionListAdapter.Section>(doRetrieveModel().categories.size)
        val mSectionedAdapter = SectionListAdapter(activity, R.layout.item_section, R.id.section_text, stageList, adapter)
        mSectionedAdapter.setSections(doRetrieveModel().categories.toArray(dummy))
        stageList.adapter = adapter
        stageList.adapter = mSectionedAdapter

        presenter.presentState(IDLE)
    }
}
