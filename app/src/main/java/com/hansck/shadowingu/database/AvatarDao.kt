package com.hansck.shadowingu.database

import android.arch.persistence.room.*
import com.hansck.shadowingu.model.Avatar
import io.reactivex.Maybe


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface AvatarDao {

	@Query("SELECT * FROM avatar")
	fun getAll(): Maybe<List<Avatar>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(avatars: Array<Avatar>)

	@Update
	fun updateAvatar(avatar: Avatar)
}