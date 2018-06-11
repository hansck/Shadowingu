package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Audio
import com.hansck.shadowingu.model.Title
import com.hansck.shadowingu.model.User

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface TitleDao {

    @Query("SELECT * FROM title")
    fun getAll(): List<Title>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(title: Title)
}