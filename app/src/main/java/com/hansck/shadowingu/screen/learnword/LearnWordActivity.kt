package com.hansck.shadowingu.screen.learnword

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.LearnWordPresenter
import com.hansck.shadowingu.presentation.presenter.LearnWordPresenter.LearnWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_learn_word.*

class LearnWordActivity : BaseActivity(), LearnWordPresenter.LearnWordView {

    private lateinit var presenter: LearnWordPresenterImpl
    private lateinit var model: LearnWordViewModel
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
        presenter.presentState(SHOW_WORD)
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
        this.model = LearnWordViewModel(this)
        this.presenter = LearnWordPresenterImpl(this)
    }

    override fun showState(viewState: LearnWordPresenter.LearnWordView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORD -> showWord()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): LearnWordViewModel = this.model

    private fun showWord() {
        bundle = intent.extras
        doRetrieveModel().setWord(bundle.getInt("idWord"))

        val word = doRetrieveModel().word
        kanji.text = word.kanji
        furigana.text = word.furigana
        romaji.text = word.romaji
        meaning.text = word.meaning
        btnVoice.setOnClickListener {
            val mPlayer = MediaPlayer.create(this, resources.getIdentifier(word.audio, "raw", packageName))
            mPlayer.setOnCompletionListener { mp -> mp.release() }
            mPlayer.start()
        }
    }
}
