package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Word
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAll(): Maybe<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(words: Array<Word>)
}