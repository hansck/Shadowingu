package com.hansck.shadowingu.screen.learnword

import android.content.Context
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.util.Manager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnWordViewModel(var context: Context?) {

    lateinit var word: Word

    fun setWord(idWord: Int) {
        word = Manager.instance.getWordById(idWord)
    }
}