package com.hansck.shadowingu.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hansck.shadowingu.model.Level
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface LevelDao {

	@Query("SELECT * FROM level")
	fun getAll(): Maybe<List<Level>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(stages: Array<Level>)
}