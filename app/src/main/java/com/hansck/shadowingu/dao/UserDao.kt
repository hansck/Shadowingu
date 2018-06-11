package com.hansck.shadowingu.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hansck.shadowingu.model.User


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Update
    fun updateUsers(user: User)
}