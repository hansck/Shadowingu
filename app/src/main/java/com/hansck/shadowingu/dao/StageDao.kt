package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Stage

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface StageDao {

    @Query("SELECT * FROM stage")
    fun getAll(): List<Stage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stage: Stage)
}