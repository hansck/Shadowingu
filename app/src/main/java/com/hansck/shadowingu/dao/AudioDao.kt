package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Audio
import com.hansck.shadowingu.model.User

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface AudioDao {

    @Query("SELECT * FROM audio")
    fun getAll(): List<Audio>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg audio: Audio)
}