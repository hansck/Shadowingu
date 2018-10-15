package com.hansck.shadowingu.screen.playword

import android.content.Context
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.PersistentManager
import java.io.File

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayWordViewModel(var context: Context?) {

	lateinit var word: Word
	lateinit var file: File
	var toggleTurn: ActiveAvatar = ActiveAvatar.PLAYER
	val REQUEST_RECORD_AUDIO_PERMISSION = 100
	val REQUEST_WRITE_STORAGE_PERMISSION = 101
	var idleAvatars = arrayOf(R.drawable.player_a_idle, R.drawable.player_b_idle, R.drawable.player_c_idle)
	var attackAvatars = arrayOf(R.drawable.player_a_attack, R.drawable.player_b_attack, R.drawable.player_c_attack)
	var damagedAvatars = arrayOf(R.drawable.player_a_damaged, R.drawable.player_b_damaged, R.drawable.player_c_damaged)
	var deadAvatars = arrayOf(R.drawable.player_a_dead, R.drawable.player_b_dead, R.drawable.player_c_dead)
	var forwardX: Float = 0F
	var backwardX: Float = 0F
	var activeAvatar: Int = 0
	var numOfFalse: Int = 0

	fun setData(idWord: Int) {
		word = DataManager.instance.getWordById(idWord)
		activeAvatar = PersistentManager.instance.getActiveAvatar()
	}

	fun getIdleAvatar(): Int = idleAvatars[activeAvatar]
	fun getAttackAvatar(): Int = attackAvatars[activeAvatar]
	fun getDamagedAvatar(): Int = damagedAvatars[activeAvatar]
	fun getDeadAvatar(): Int = deadAvatars[activeAvatar]
	fun isShowHint(): Boolean = numOfFalse >= Constants.General.MAX_WRONG_ANSWERS
}