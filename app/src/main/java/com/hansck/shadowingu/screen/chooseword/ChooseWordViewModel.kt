package com.hansck.shadowingu.screen.chooseword

import android.content.Context
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ChooseWordViewModel(var context: Context?) {

    var words: List<Word> = ArrayList()

    fun setWords(idStage: Int) {
        words = DataManager.instance.getWordsByStage(idStage)
    }
}