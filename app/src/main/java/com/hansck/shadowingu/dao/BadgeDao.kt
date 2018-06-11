package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Badge

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface BadgeDao {

    @Query("SELECT * FROM badge")
    fun getAll(): List<Badge>

    @Insert(onConflict = REPLACE)
    fun insert(badge: Badge)
}