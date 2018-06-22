package com.hansck.shadowingu.screen.chooseword

import android.content.Context
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ChooseWordViewModel(var context: Context?) {

    var words: ArrayList<Word> = ArrayList()

    fun setWords() {
        words = DataManager.instance.words
    }

    fun getWordsByStage(stage: Int): List<Word> {
        return words.filter { it.stage == stage }
    }
}