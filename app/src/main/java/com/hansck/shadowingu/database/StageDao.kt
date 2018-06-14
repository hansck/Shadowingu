package com.hansck.shadowingu.database

import android.arch.persistence.room.*
import com.hansck.shadowingu.model.Stage
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface StageDao {

    @Query("SELECT * FROM stage")
    fun getAll(): Maybe<List<Stage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stages: Array<Stage>)

    @Update
    fun updateStage(stage: Stage)
}