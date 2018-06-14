package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Audio
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface AudioDao {

    @Query("SELECT * FROM audio")
    fun getAll(): Maybe<List<Audio>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(audios: Array<Audio>)
}