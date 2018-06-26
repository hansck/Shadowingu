package com.hansck.shadowingu.screen.playword

import android.content.Context
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class PlayWordViewModel(var context: Context?) {

    lateinit var word: Word

    fun setWord(idWord: Int) {
        word = DataManager.instance.getWordById(idWord)
    }
}