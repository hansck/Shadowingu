package com.hansck.shadowingu.screen.play

import android.content.Context
import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.PersistentManager
import smartdevelop.ir.eram.showcaseviewlib.GuideView

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayViewModel(var context: Context?) {

	var words: List<Word> = ArrayList()
	var badges: List<Badge> = ArrayList()
	var levels: List<Level> = ArrayList()
	var updatedBadges: ArrayList<Badge> = ArrayList()
	var hearts: ArrayList<Heart> = ArrayList()
	lateinit var guides: Array<GuideView>
	lateinit var avatar: Avatar
	lateinit var lesson: Lesson
	lateinit var user: User
	var currentWordId: Int = 0
	var count: Int = 10
	var oldLevel: Int = 0
	var oldExp: Int = 0
	var timeStart: Long = 0
	var timeEnd: Long = 0
	var timeElapsed: Long = 0
	var numOfHearts: Int = 0
	var skippedWords: Int = 0
	var guideIdx: Int = 0

	// For Badges Checking
	var isGameOver: Boolean = false
	var isPerfect: Boolean = false
	var isLevelUp: Boolean = false

	// For Win Result
	var isNewTimeRecord: Boolean = false
	var isScoreRecord: Boolean = false

	fun setData(idStage: Int) {
		lesson = DataManager.instance.lessons[idStage]
		user = DataManager.instance.user
		badges = DataManager.instance.badges
		words = DataManager.instance.getWordsByStage(idStage)
		levels = DataManager.instance.levels
		avatar = DataManager.instance.getActiveAvatar()
		currentWordId = words[0].idWord
		generateHearts()
	}

	fun getGuide(): GuideView = guides[guideIdx]
	fun checkGuides(): Boolean = guideIdx < guides.size
	fun getScore(): Int = Constants.General.WORDS_PER_LEVEL - skippedWords

	fun setCount() {
		if (count == 10) timeStart = System.currentTimeMillis()
		currentWordId++
		count--
	}

	fun resetPlay() {
		count = 10; currentWordId = words[0].idWord; oldLevel = 0; oldExp = 0; timeStart = 0; timeEnd = 0; timeElapsed = 0
		isPerfect = false; isGameOver = false; isPerfect = false; isLevelUp = false; isNewTimeRecord = false
		skippedWords = 0
		generateHearts()
	}

	fun calculatePlayResult() {
		//Calculate Time
		timeEnd = System.currentTimeMillis()
		timeElapsed = (timeEnd - timeStart) / 1000

		//Calculate Level and Exp
		val userExp = user.exp + lesson.exp
		val expToLevelUp = levels[user.level - 1].exp - userExp

		oldLevel = user.level
		oldExp = user.exp
		when {
			user.level == Constants.General.MAX_LEVEL -> user.exp = userExp
			expToLevelUp <= 0 -> {
				user.level++
				user.exp = expToLevelUp * -1
				isLevelUp = true
			}
			else -> user.exp = userExp
		}
		user.gem++
		DataManager.instance.user = user
		checkLesson()
		checkBadges()
	}

	private fun checkLesson() {
		lesson.cleared = true
		if (timeElapsed < lesson.fastestTime || lesson.fastestTime.toInt() == 0) {
			lesson.fastestTime = timeElapsed
			isNewTimeRecord = true
		}
		if (getScore() > lesson.high_score) {
			lesson.high_score = getScore()
			isScoreRecord = true
		}
		DataManager.instance.lessons[lesson.idLesson] = lesson
	}

	private fun checkBadges() {
		if (numOfHearts == hearts.size && !PersistentManager.instance.isPerfectPlay()) {
			PersistentManager.instance.setPerfectPlay()
			updateBadge(0)
		}
		if (lesson.idLesson == Constants.General.MAX_LESSON - 1 && !PersistentManager.instance.isAllStagesCleared()) {
			PersistentManager.instance.setAllStagesCleared()
			updateBadge(1)
		}
		if (user.level == Constants.General.MAX_LEVEL && !PersistentManager.instance.isMaxLevel()) {
			PersistentManager.instance.setMaxLevel()
			updateBadge(3)
		}
	}

	fun checkGameOverBadge(): Boolean {
		if (!PersistentManager.instance.isFirstGameOver()) {
			PersistentManager.instance.setFirstGameOver()
			updateBadge(4)
			isGameOver = true
		}
		return isGameOver
	}

	private fun updateBadge(index: Int) {
		badges[index].unlock = true
		updatedBadges.add(badges[index])
		DataManager.instance.badges[index] = badges[index]
	}

	private fun generateHearts() {
		hearts.clear()
		var count = 0
		while (count < avatar.hearts) {
			hearts.add(Heart(0, true, "ic_heart_full", "ic_heart_empty"))
			count++
		}
		numOfHearts = hearts.size
	}

	fun reduceHeart(): Int {
		if (numOfHearts - 1 >= 0) {
			hearts[numOfHearts - 1].status = false
			numOfHearts--
		}
		return numOfHearts
	}
}