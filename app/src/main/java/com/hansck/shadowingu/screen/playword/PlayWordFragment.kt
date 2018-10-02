package com.hansck.shadowingu.screen.playword

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.screen.playword.ActiveAvatar.ENEMY
import com.hansck.shadowingu.screen.playword.ActiveAvatar.PLAYER
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.SimilarityMatching
import kotlinx.android.synthetic.main.fragment_play_word.*
import omrecorder.*
import java.io.File


class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView, VoiceSimilarityListener, Animation.AnimationListener {

    private lateinit var model: PlayWordViewModel
    private lateinit var presenter: PlayWordPresenter
    private lateinit var bundle: Bundle
    private lateinit var recorder: Recorder
    private val REQUEST_RECORD_AUDIO_PERMISSION = 100
    private val REQUEST_WRITE_STORAGE_PERMISSION = 101
    private var toggleTurn: ActiveAvatar = PLAYER
    private lateinit var file: File
    private var forwardX: Float = 0F
    private var backwardX: Float = 0F

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
            CORRECT_ANSWER -> playerTurn()
            WRONG_ANSWER -> enemyTurn()
            ERROR -> showError(null, getString(R.string.failed_request_general))
        }
    }

    override fun doRetrieveModel(): PlayWordViewModel = this.model

    override fun onSimilarityCalculated(distance: Double) {
        Log.e("SIMILARITY", distance.toString())
        Log.e("END", "------------------------------------------------------------------------------------------")
        if (distance < 15) {
            presenter.presentState(CORRECT_ANSWER)
        } else {
            presenter.presentState(WRONG_ANSWER)
        }
//        file.delete()
    }

    private fun showWord() {
        bundle = this.arguments!!
        doRetrieveModel().setWord(bundle.getInt("idWord"))

        initAvatars()

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
            presenter.presentState(WRONG_ANSWER)
            descriptionContainer.visibility = View.VISIBLE
            description.visibility = View.GONE
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
                        SimilarityMatching.instance.calculateSimilarity(activity!!,
                                resources.getIdentifier(word.audio, "raw", activity!!.packageName),
                                file, 0, this, 1)
                    }
                }
            }
            false
        })
    }

    //region Animation
    private fun playerTurn() {
        toggleTurn = PLAYER
        playerAttack()
    }

    private fun enemyTurn() {
        toggleTurn = ENEMY
        enemyAttack()
    }

    private fun animateAvatar(view: ImageView, drawable: Int) {
        view.setImageResource(drawable)
        val animation = view.drawable as AnimationDrawable
        animation.start()
    }

    private fun checkEnemyHP() {
        playerIdle()
        enemyDead()
        Handler().postDelayed({
            if (activity != null)
                (activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WORD_SCREEN)
        }, 1200)
    }

    private fun checkPlayerHP() {
        enemyIdle()
        val act = (activity as PlayActivity)
        act.presenter.presentState(PlayPresenter.PlayView.ViewState.REDUCE_HEARTS)
        if (act.doRetrieveModel().reduceHeart() > 0) {
            playerDamaged()
        } else {
            playerDead()
            Handler().postDelayed({
                act.presenter.presentState(PlayPresenter.PlayView.ViewState.PLAYER_DEAD)
            }, 1200)
        }
    }

    private fun initAvatars() {
        playerIdle()
        enemyIdle()
    }

    private fun playerIdle() {
        animateAvatar(player, R.drawable.player_idle)
    }

    private fun playerAttack() {
        animateAvatar(player, R.drawable.enemy_attack)
        animateAttackBall(player_ball)
    }

    private fun playerDamaged() {
        animateAvatar(player, R.drawable.enemy_attack)
    }

    private fun playerDead() {
        animateAvatar(player, R.drawable.enemy_dead)
    }

    private fun enemyIdle() {
        animateAvatar(enemy, R.drawable.enemy_idle)
    }

    private fun enemyAttack() {
        animateAvatar(enemy, R.drawable.enemy_attack)
        animateAttackBall(enemy_ball)
    }

    private fun enemyDead() {
        animateAvatar(enemy, R.drawable.enemy_dead)
    }

    private fun animateAttackBall(view: View) {
        if (forwardX == 0F && backwardX == 0F) checkScreenSize()
        val animation = if (toggleTurn == PLAYER) {
            TranslateAnimation(-backwardX, forwardX, 0F, 0F)
        } else {
            TranslateAnimation(backwardX, -forwardX, 0F, 0F)
        }
        animation.duration = 750
        animation.fillAfter = false
        animation.setAnimationListener(this)
        view.startAnimation(animation)
    }

    private fun checkScreenSize() {
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        forwardX = width * 500F / 1080F
        backwardX = width * 100F / 1080F
    }

    override fun onAnimationEnd(animation: Animation) {
        if (toggleTurn == PLAYER) {
            player_ball.clearAnimation()
            player_ball.visibility = View.GONE
            checkEnemyHP()
        } else {
            enemy_ball.clearAnimation()
            enemy_ball.visibility = View.GONE
            checkPlayerHP()
        }
    }

    override fun onAnimationRepeat(animation: Animation) {}

    override fun onAnimationStart(animation: Animation) {
        if (toggleTurn == PLAYER) {
            player_ball.visibility = View.VISIBLE
        } else {
            enemy_ball.visibility = View.VISIBLE
        }
    }
    //endregion

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
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/" + activity!!.getString(R.string.app_name))
        if (!folder.exists()) folder.mkdir()
        val filename = activity!!.getString(R.string.app_name) + "/" + System.currentTimeMillis().toString() + ".wav"
        file = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            File(Environment.getExternalStorageDirectory(), filename)
        } else {
            File(context!!.filesDir, filename)
        }
    }

    private fun startRecording() {
        btnRemark.text = activity!!.getString(R.string.release_to_stop_shadowing)
        prepareFile()
        recorder = OmRecorder.wav(PullTransport.Noise(mic(),
                PullTransport.OnAudioChunkPulledListener { },
                WriteAction.Default(),
                Recorder.OnSilenceListener {}, 200
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
        when {
            ContextCompat.checkSelfPermission(activity!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ->
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
            ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ->
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_STORAGE_PERMISSION)
            else -> startRecording()
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

enum class ActiveAvatar {
    PLAYER, ENEMY
}