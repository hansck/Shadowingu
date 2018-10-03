package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hansck.shadowingu.model.User
import io.reactivex.Maybe


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface UserDao {

	@Query("SELECT * FROM user")
	fun getAll(): Maybe<List<User>>

	@Insert(onConflict = REPLACE)
	fun insert(user: User)

	@Update
	fun updateUser(user: User)
}