package com.hansck.shadowingu.screen.play

import android.content.Context
import android.util.Log
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayViewModel(var context: Context?) {

    var words: List<Word> = ArrayList()
    var currentWordId: Int = 1

    fun setWords(idStage: Int) {
        words = DataManager.instance.getWordsByStage(idStage)
        currentWordId = words[0].idWord
    }
}