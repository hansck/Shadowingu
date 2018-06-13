package com.hansck.shadowingu.screen.chooseword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.ChooseWordPresenter
import com.hansck.shadowingu.presentation.presenter.ChooseWordPresenter.ChooseWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ChooseWordFragment : BaseFragment(), ChooseWordPresenter.ChooseWordView {

    private lateinit var presenter: ChooseWordPresenterImpl
    private lateinit var model: ChooseWordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(LOAD_WORDS)
    }

    private fun init() {
        this.model = ChooseWordViewModel(activity)
        this.presenter = ChooseWordPresenterImpl(this)
    }

    override fun showState(viewState: ChooseWordPresenter.ChooseWordView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORDS -> showWords()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): ChooseWordViewModel = this.model

    private fun showWords() {

    }
}
