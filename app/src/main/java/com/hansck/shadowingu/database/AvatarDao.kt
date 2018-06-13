package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Avatar
import android.arch.persistence.room.Update
import io.reactivex.Maybe


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface AvatarDao {

    @Query("SELECT * FROM avatar")
    fun getAll(): Maybe<List<Avatar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg avatars: Avatar)

    @Update
    fun updateAvatar(avatar: Avatar)
}