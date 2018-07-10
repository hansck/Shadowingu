package com.hansck.shadowingu.screen.play

import android.content.Context
import android.util.Log
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
    var hearts: ArrayList<Heart> = ArrayList()
    lateinit var avatar: Avatar
    lateinit var stage: Stage
    lateinit var user: User
    var currentWordId: Int = 0
    var count: Int = 10
    var oldLevel: Int = 0
    var oldExp: Int = 0
    var timeStart: Long = 0
    var timeEnd: Long = 0
    var timeElapsed: Long = 0
    var isPerfect: Boolean = false
    var isGameOver: Boolean = false
    var isLevelUp: Boolean = false
    var isNewRecord: Boolean = false

    fun setData(idStage: Int) {
        Log.e("STAGES", DataManager.instance.stages.size.toString() + " " + idStage)
        stage = DataManager.instance.stages[idStage]
        user = DataManager.instance.user
        badges = DataManager.instance.badges
        words = DataManager.instance.getWordsByStage(idStage)
        levels = DataManager.instance.levels
        avatar = DataManager.instance.getActiveAvatar()
        currentWordId = words[0].idWord
        generateHearts()
    }

    fun setCount() {
        if (count == 10) timeStart = System.currentTimeMillis()
        currentWordId++
        count--
    }

    fun resetPlay() {
        count = 10; currentWordId = words[0].idWord; oldLevel = 0; oldExp = 0; timeStart = 0; timeEnd = 0; timeElapsed = 0
        isPerfect = false; isPerfect = false; isGameOver = false; isLevelUp = false; isNewRecord = false
        generateHearts()
    }

    fun calculatePlayResult() {
        //Calculate Time
        timeEnd = System.currentTimeMillis()
        timeElapsed = (timeEnd - timeStart) / 1000

        //Calculate Level and Exp
        val userExp = user.exp + stage.exp
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
        DataManager.instance.user = user
        checkStage()
        checkBadges()
    }

    private fun checkStage(): Boolean {
        stage.cleared = true
        if (timeElapsed < stage.fastestTime || stage.fastestTime.toInt() == 0) {
            stage.fastestTime = timeElapsed.toLong()
            isNewRecord = true
        }
        return isNewRecord
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

    fun generateHearts() {
        hearts.clear()
        var count = 0
        while (count < avatar.hearts) {
            hearts.add(Heart(0, true, "ic_heart_full", "ic_heart_empty"))
            count++
        }
    }
}