package com.hansck.shadowingu.screen.playword

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.Common
import kotlinx.android.synthetic.main.fragment_play_word.*

class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView {

    private lateinit var model: PlayWordViewModel
    private lateinit var presenter: PlayWordPresenter
    private lateinit var bundle: Bundle
    private var toggleDesc: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.presentState(SHOW_WORD)
    }

    private fun init() {
        this.model = PlayWordViewModel(activity)
        this.presenter = PlayWordPresenterImpl(this)
    }

    override fun showState(viewState: PlayWordPresenter.PlayWordView.ViewState) {
        when (viewState) {
            IDLE -> showProgress(false)
            LOADING -> showProgress(true)
            SHOW_WORD -> showWord()
            NEXT_WORD -> nextWord()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayWordViewModel = this.model

    private fun showWord() {
        bundle = this.arguments!!
        doRetrieveModel().setWord(bundle.getInt("idWord"))

        val word = doRetrieveModel().word
        kanji.text = word.kanji
        furigana.text = word.furigana
        romaji.text = word.romaji
        meaning.text = word.meaning
        btnVoice.setOnClickListener {
            val mPlayer = MediaPlayer.create(activity, Common.instance.getResourceId(activity!!, "raw", word.audio))
            mPlayer.setOnCompletionListener { mp -> mp.release() }
            mPlayer.start()
        }
        description.setOnClickListener {
            toggleDesc = !toggleDesc
            if (toggleDesc) {
                descriptionContainer.visibility = View.VISIBLE
            } else {
                descriptionContainer.visibility = View.GONE
            }
        }
        btnRecording.setOnClickListener {
            presenter.presentState(NEXT_WORD)
        }
    }

    private fun nextWord() {
        (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WORD_SCREEN)
    }
}
