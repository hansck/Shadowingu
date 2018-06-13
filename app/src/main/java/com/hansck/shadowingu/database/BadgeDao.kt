package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hansck.shadowingu.model.Avatar
import com.hansck.shadowingu.model.Badge
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface BadgeDao {

    @Query("SELECT * FROM badge")
    fun getAll(): Maybe<List<Badge>>

    @Insert(onConflict = REPLACE)
    fun insertAll(vararg badges: Badge)

    @Update
    fun updateBadge(badge: Badge)
}