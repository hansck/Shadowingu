package com.hansck.shadowingu.screen.playword

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
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
import java.io.File


class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView, VoiceSimilarityListener {

    private lateinit var model: PlayWordViewModel
    private lateinit var presenter: PlayWordPresenter
    private lateinit var bundle: Bundle
    private lateinit var recorder: MediaRecorder
    private val REQUEST_RECORD_AUDIO_PERMISSION = 100
    private val REQUEST_WRITE_STORAGE_PERMISSION = 101
    private var toggleDesc: Boolean = false
    private lateinit var file: File
    private var filename: String = ""

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

//                        SimilarityMatching.getInstance().calculateSimilarity(activity,
//                                resources.getIdentifier("watashi", "raw", activity!!.packageName),
//                                getFile(),
//                                this, 1)
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
    private fun getFilename(): String {
        val filepath = Environment.getExternalStorageDirectory().path
        file = File(filepath, activity!!.getString(R.string.app_name))

        if (!file.exists()) {
            file.mkdirs()
        }

        filename = System.currentTimeMillis().toString() + ".mp3"
        return file.absolutePath + "/" + filename
    }

    private fun getFile(): File = File(this.file.absolutePath, filename)

    private fun startRecording() {
        btnRemark.text = activity!!.getString(R.string.release_to_stop_shadowing)

        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder.setAudioSamplingRate(44100)
        recorder.setAudioEncodingBitRate(384000)
        recorder.setOutputFile(getFilename())
        recorder.setOnErrorListener(errorListener)
        recorder.setOnInfoListener(infoListener)

        recorder.prepare()
        recorder.start()
    }

    private val errorListener = MediaRecorder.OnErrorListener { mr, what, extra -> Log.e("Error", what.toString() + extra) }

    private val infoListener = MediaRecorder.OnInfoListener { mr, what, extra -> Log.e("Error", what.toString() + extra) }

    private fun stopRecording() {
        btnRemark.text = activity!!.getString(R.string.press_to_start_shadowing)
        recorder.stop()
        recorder.reset()
        recorder.release()
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
