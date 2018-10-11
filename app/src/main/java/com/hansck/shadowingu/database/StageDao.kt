package com.hansck.shadowingu.database

import android.arch.persistence.room.*
import com.hansck.shadowingu.model.Lesson
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface StageDao {

	@Query("SELECT * FROM lesson")
	fun getAll(): Maybe<List<Lesson>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(lessons: Array<Lesson>)

	@Update
	fun updateStage(lesson: Lesson)
}