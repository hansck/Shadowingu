package com.hansck.shadowingu.screen.playword

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.SimilarityMatching
import kotlinx.android.synthetic.main.fragment_play_word.*
import omrecorder.*
import java.io.File


class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView, VoiceSimilarityListener {

    private lateinit var model: PlayWordViewModel
    private lateinit var presenter: PlayWordPresenter
    private lateinit var bundle: Bundle
    private lateinit var recorder: Recorder
    private val REQUEST_RECORD_AUDIO_PERMISSION = 100
    private val REQUEST_WRITE_STORAGE_PERMISSION = 101
    private var toggleDesc: Boolean = false
    private lateinit var file: File

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.hide()
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
            CORRECT_ANSWER -> correctAnswer()
            WRONG_ANSWER -> wrongAnswer()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayWordViewModel = this.model

    override fun onSimilarityCalculated(distance: Double) {
        Log.e("SIMILARITY", distance.toString())
        Log.e("END", "------------------------------------------------------------------------------------------")
        Toast.makeText(activity, distance.toString(), Toast.LENGTH_SHORT).show()
        if (distance < 15) {
            presenter.presentState(CORRECT_ANSWER)
        } else {
            presenter.presentState(WRONG_ANSWER)
        }
//        getFile().delete()
    }

    private fun showWord() {
        bundle = this.arguments!!
        doRetrieveModel().setWord(bundle.getInt("idWord"))

        showAvatar()

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
        btnRecording.setOnTouchListener(View.OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    checkPermissions()
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    if (::recorder.isInitialized) {
                        stopRecording()
                        SimilarityMatching.getInstance().calculateSimilarity(activity,
                                resources.getIdentifier("watashi", "raw", activity!!.packageName),
                                file,
                                0,
                                this, 1)
                    }
                }
            }
            false
        })
    }

    private fun showAvatar() {
        player.setImageResource(R.drawable.player)
        val playerAnimation = player.drawable as AnimationDrawable
        playerAnimation.start()

        enemy.setImageResource(R.drawable.enemy)
        val enemyAnimation = enemy.drawable as AnimationDrawable
        enemyAnimation.start()
    }

    private fun correctAnswer() {
        (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WORD_SCREEN)
    }

    private fun wrongAnswer() {
        (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WRONG)
    }

    //region Audio Recording
    private fun mic(): PullableSource {
        return PullableSource.Default(
                AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                )
        )
    }

    private fun prepareFile() {
        val filename = activity!!.getString(R.string.app_name) + "/" + System.currentTimeMillis().toString() + ".wav"
        file = File(Environment.getExternalStorageDirectory(), filename)
    }

    private fun startRecording() {
        btnRemark.text = activity!!.getString(R.string.release_to_stop_shadowing)
        prepareFile()
        recorder = OmRecorder.wav(PullTransport.Noise(mic(),
                PullTransport.OnAudioChunkPulledListener { },
                WriteAction.Default(),
                Recorder.OnSilenceListener { silenceTime ->
                    Log.e("silenceTime", silenceTime.toString())
                    Toast.makeText(activity, "silence of $silenceTime detected", Toast.LENGTH_SHORT).show()
                }, 200
        ), file)
        recorder.startRecording()
    }

    private fun stopRecording() {
        btnRemark.text = activity!!.getString(R.string.press_to_start_shadowing)
        recorder.stopRecording()
    }
    //endregion

    //region Ask for Permission
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        } else if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE_PERMISSION)
        } else {
            startRecording()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> checkPermissions()
            REQUEST_WRITE_STORAGE_PERMISSION -> checkPermissions()
        }
    }
//endregion
}
