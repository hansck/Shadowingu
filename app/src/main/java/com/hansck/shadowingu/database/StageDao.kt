package com.hansck.shadowingu.database

import android.arch.persistence.room.*
import com.hansck.shadowingu.model.Topic
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface StageDao {

    @Query("SELECT * FROM topic")
    fun getAll(): Maybe<List<Topic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(topics: Array<Topic>)

    @Update
    fun updateStage(topic: Topic)
}