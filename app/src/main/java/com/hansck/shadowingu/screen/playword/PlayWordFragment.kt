package com.hansck.shadowingu.screen.playword

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.media.AudioFormat
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.SpeechSimilarityListener
import com.hansck.shadowingu.presentation.presenter.PlayPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter
import com.hansck.shadowingu.presentation.presenter.PlayWordPresenter.PlayWordView.ViewState.*
import com.hansck.shadowingu.screen.base.BaseFragment
import com.hansck.shadowingu.screen.play.PlayActivity
import com.hansck.shadowingu.screen.playword.ActiveAvatar.ENEMY
import com.hansck.shadowingu.screen.playword.ActiveAvatar.PLAYER
import com.hansck.shadowingu.util.CalculationMatching
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.PersistentManager
import kotlinx.android.synthetic.main.fragment_play_word.*
import omrecorder.*
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import java.io.File


class PlayWordFragment : BaseFragment(), PlayWordPresenter.PlayWordView, SpeechSimilarityListener, Animation.AnimationListener {

	private lateinit var model: PlayWordViewModel
	private lateinit var presenter: PlayWordPresenter
	private lateinit var bundle: Bundle
	private lateinit var recorder: Recorder

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

	override fun onSimilarityMatching(isSimilar: Boolean) {
		if (isSimilar) {
			presenter.presentState(CORRECT_ANSWER)
		} else {
			presenter.presentState(WRONG_ANSWER)
		}
//		doRetrieveModel().file?.delete()
	}

	private fun showWord() {
		bundle = this.arguments!!
		doRetrieveModel().setData(bundle.getInt("idWord"))
		initAvatars()

		val word = doRetrieveModel().word
		kanji.text = word.kanji
		furigana.text = word.furigana
		romaji.text = word.romaji
		meaning.text = word.meaning
		Common.instance.setImageByName(activity!!, (activity as PlayActivity).doRetrieveModel().lesson.arena, background)
		btnVoice.setOnClickListener { playAudio() }
		voiceContainer.setOnClickListener { playAudio() }
		btnHint.setOnClickListener {
			descriptionContainer.visibility = View.VISIBLE
			btnHint.visibility = View.GONE
		}
		btnSkip.setOnClickListener {
			(activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SKIP_WORD)
		}
		frameRecording.setOnTouchListener(listener)
		btnRecording.setOnTouchListener(listener)

		if (!PersistentManager.instance.isShowGuide()) {
			doRetrieveModel().guides = arrayOf(buildGuide(kanji, "Word", resources.getString(R.string.guide_word)),
					buildGuide(btnVoice, "Voice", resources.getString(R.string.guide_voice)),
					buildGuide(btnHint, "Hint", resources.getString(R.string.guide_hint)),
					buildGuide(btnRecording, "Recording", resources.getString(R.string.guide_recording)))
			showGuide()
		} else {
			Handler().postDelayed({
				playAudio()
			}, 500)
		}
	}

	private fun showGuide() {
		if (doRetrieveModel().checkGuides()) {
			doRetrieveModel().getGuide().show()
		} else {
			PersistentManager.instance.setShowGuide()
			playAudio()
			(activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.START_TIMER)
		}
	}

	private fun buildGuide(view: View, title: String, content: String): GuideView {
		return GuideView.Builder(activity)
				.setTitle(title)
				.setContentText(content)
				.setTargetView(view)
				.setDismissType(GuideView.DismissType.anywhere)
				.setGuideListener {
					doRetrieveModel().guideIdx++
					showGuide()
				}
				.build()
	}

	private fun playAudio() {
		if (activity != null)
			Common.instance.playAudio(activity!!, doRetrieveModel().word.audio)
	}

	private val listener = View.OnTouchListener { _, event ->
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				checkPermissions()
				return@OnTouchListener true
			}
			MotionEvent.ACTION_UP -> {
				if (::recorder.isInitialized) {
					stopRecording()
					if (Common.instance.checkAudioDuration(activity!!, Uri.parse(Uri.fromFile(doRetrieveModel().file).toString()))) {
						CalculationMatching.instance.calculateSimilarity(activity!!,
								doRetrieveModel().word.idWord, doRetrieveModel().file, this)
					} else {
						presenter.presentState(WRONG_ANSWER)
					}
				}
			}
		}
		false
	}

	//region Animation
	private fun playerTurn() {
		doRetrieveModel().toggleTurn = PLAYER
		animateProgressBar(100F, R.color.color_accent, false)
		Common.instance.playAudio(activity!!, "bgs_correct")
		playerAttack()
		btnRecording.isEnabled = false
		frameRecording.isEnabled = false
	}

	private fun enemyTurn() {
		doRetrieveModel().toggleTurn = ENEMY
		animateProgressBar(100F, R.color.ic_cancel, false)
		Common.instance.playAudio(activity!!, "bgs_wrong")
		enemyAttack()
		doRetrieveModel().numOfFalse++
	}

	private fun animateAvatar(view: ImageView?, drawable: Int) {
		if (view != null) {
			view.setImageResource(drawable)
			val animation = view.drawable as AnimationDrawable
			animation.start()
		}
	}

	private fun checkEnemyHP() {
		Common.instance.playAudio(activity!!, "bgs_splash")
		playerIdle()
		enemyDead()
		Handler().postDelayed({
			if (activity != null)
				(activity as PlayActivity).presenter.presentState(PlayPresenter.PlayView.ViewState.SHOW_WORD_SCREEN)
		}, 1200)
	}

	private fun checkPlayerHP() {
		Common.instance.playAudio(activity!!, "bgs_explosion")
		enemyIdle()
		if (doRetrieveModel().isShowHint()) btnSkip.visibility = View.VISIBLE
		val act = (activity as PlayActivity)
		act.presenter.presentState(PlayPresenter.PlayView.ViewState.REDUCE_HEARTS)
		if (act.doRetrieveModel().reduceHeart() > 0) {
			playerDamaged()
			Handler().postDelayed({
				playerIdle()
				animateProgressBar(0F, R.color.ic_cancel, true)
			}, 700)
		} else {
			playerDead()
			Handler().postDelayed({
				act.presenter.presentState(PlayPresenter.PlayView.ViewState.PLAYER_DEAD)
			}, 1000)
		}
	}

	private fun initAvatars() {
		playerIdle()
		enemyIdle()
	}

	private fun playerIdle() {
		animateAvatar(player, doRetrieveModel().getIdleAvatar())
	}

	private fun playerAttack() {
		animateAvatar(player, doRetrieveModel().getAttackAvatar())
		animateAttackBall(player_ball)
	}

	private fun playerDamaged() {
		animateAvatar(player, doRetrieveModel().getDamagedAvatar())
	}

	private fun playerDead() {
		animateAvatar(player, doRetrieveModel().getDeadAvatar())
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

	private fun animateAttackBall(view: View?) {
		if (view != null) {
			if (doRetrieveModel().forwardX == 0F && doRetrieveModel().backwardX == 0F) checkScreenSize()
			val animation = if (doRetrieveModel().toggleTurn == PLAYER) {
				TranslateAnimation(-doRetrieveModel().backwardX, doRetrieveModel().forwardX, 0F, 0F)
			} else {
				TranslateAnimation(doRetrieveModel().backwardX, -doRetrieveModel().forwardX, 0F, 0F)
			}
			animation.duration = 750
			animation.fillAfter = false
			animation.setAnimationListener(this)
			view.startAnimation(animation)
		}
	}

	private fun checkScreenSize() {
		val displayMetrics = DisplayMetrics()
		activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
		val width = displayMetrics.widthPixels
		doRetrieveModel().forwardX = width * 500F / 1080F
		doRetrieveModel().backwardX = width * 100F / 1080F
	}

	private fun animateProgressBar(progress: Float, color: Int, isBack: Boolean) {
		if (progressBar != null) {
			if (!isBack) progressBar.color = ContextCompat.getColor(activity!!, color)
			progressBar.setProgressWithAnimation(progress, 500)
		}
	}

	override fun onAnimationEnd(animation: Animation) {
		if (doRetrieveModel().toggleTurn == PLAYER) {
			if (player_ball != null) {
				player_ball.clearAnimation()
				player_ball.visibility = View.GONE
				checkEnemyHP()
			}
		} else {
			if (enemy_ball != null) {
				enemy_ball.clearAnimation()
				enemy_ball.visibility = View.GONE
				checkPlayerHP()
			}
		}
	}

	override fun onAnimationRepeat(animation: Animation) {}

	override fun onAnimationStart(animation: Animation) {
		if (doRetrieveModel().toggleTurn == PLAYER) {
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

		doRetrieveModel().file = null
		doRetrieveModel().file = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
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
		), doRetrieveModel().file)
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
				ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO), doRetrieveModel().REQUEST_RECORD_AUDIO_PERMISSION)
			ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ->
				ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), doRetrieveModel().REQUEST_WRITE_STORAGE_PERMISSION)
			else -> startRecording()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		when (requestCode) {
			doRetrieveModel().REQUEST_RECORD_AUDIO_PERMISSION -> checkPermissions()
			doRetrieveModel().REQUEST_WRITE_STORAGE_PERMISSION -> checkPermissions()
		}
	}
	//endregion
}

enum class ActiveAvatar {
	PLAYER, ENEMY
}