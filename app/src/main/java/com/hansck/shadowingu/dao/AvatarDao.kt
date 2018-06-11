package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Avatar

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface AvatarDao {

    @Query("SELECT * FROM avatar")
    fun getAll(): List<Avatar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(avatar: Avatar)
}