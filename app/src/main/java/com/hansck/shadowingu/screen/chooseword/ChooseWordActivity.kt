package com.hansck.shadowingu.screen.chooseword

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.presentation.adapter.WordAdapter
import com.hansck.shadowingu.presentation.customview.OnWordSelected
import com.hansck.shadowingu.presentation.presenter.ChooseWordPresenter
import com.hansck.shadowingu.presentation.presenter.ChooseWordPresenter.ChooseWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.screen.learnword.LearnWordActivity
import kotlinx.android.synthetic.main.activity_choose_word.*

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ChooseWordActivity : BaseActivity(), ChooseWordPresenter.ChooseWordView, OnWordSelected {

    private lateinit var model: ChooseWordViewModel
    private lateinit var presenter: ChooseWordPresenter
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_word)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
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
        this.model = ChooseWordViewModel(this)
        this.presenter = ChooseWordPresenterImpl(this)
        presenter.presentState(SHOW_WORDS)
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

    override fun onWordSelected(word: Word) {
        val intent = Intent(this, LearnWordActivity::class.java)
        intent.putExtra("idWord", word.idWord)
        startActivity(intent)
    }

    private fun showWords() {
        bundle = intent.extras
        doRetrieveModel().setWords(bundle.getInt("idStage"))
        bundle = intent.extras
        wordList.setHasFixedSize(true)
        wordList.layoutManager = GridLayoutManager(this, 2)
        wordList.adapter = WordAdapter(ArrayList(doRetrieveModel().words), this)
    }
}
