package com.hansck.shadowingu.screen.play

import android.content.Context
import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.PersistentManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayViewModel(var context: Context?) {

    var words: List<Word> = ArrayList()
    var badges: List<Badge> = ArrayList()
    var levels: List<Level> = ArrayList()
    var updatedBadges: ArrayList<Badge> = ArrayList()
    lateinit var stage: Stage
    lateinit var user: User
    var currentWordId: Int = 0
    var count: Int = 10
    var timeStart: Long = 0
    var timeEnd: Long = 0
    var timeElapsed: Double = 0.0
    var isPerfect: Boolean = true
    var isGameOver: Boolean = false
    var isLevelUp: Boolean = false

    fun setData(idStage: Int) {
        stage = DataManager.instance.stages[idStage]
        user = DataManager.instance.user
        badges = DataManager.instance.badges
        words = DataManager.instance.getWordsByStage(idStage)
        levels = DataManager.instance.levels
        currentWordId = words[0].idWord
    }

    fun setCount() {
        if (count == 10) timeStart = System.currentTimeMillis()
        currentWordId++
        count--
    }

    fun calculatePlayResult() {
        //Calculate Time
        timeEnd = System.currentTimeMillis()
        timeElapsed = (timeEnd - timeStart) / 1000.0

        //Calculate Level and Exp
        val userExp = user.exp + stage.exp
        val expToLevelUp = levels[user.level].exp - userExp

        if (expToLevelUp <= 0) {
            user.level++
            user.exp = expToLevelUp * -1
            isLevelUp = true
        } else {
            user.exp = userExp
        }
        DataManager.instance.user = user
        checkBadges()
    }

    private fun checkBadges() {
        if (isPerfect && !PersistentManager.instance.isPerfectPlay()) {
            PersistentManager.instance.setPerfectPlay()
            updatedBadges.add(badges[0])
        }
        if (stage.idStage == Constants.General.MAX_STAGE && PersistentManager.instance.isAllStagesCleared()) {
            PersistentManager.instance.setAllStagesCleared()
            updatedBadges.add(badges[1])
        }
        if (user.level == Constants.General.MAX_LEVEL && PersistentManager.instance.isMaxLevel()) {
            PersistentManager.instance.setMaxLevel()
            updatedBadges.add(badges[3])
        }
        if (isGameOver && !PersistentManager.instance.isFirstGameOver()) {
            PersistentManager.instance.setFirstGameOver()
            updatedBadges.add(badges[4])
        }
    }
}