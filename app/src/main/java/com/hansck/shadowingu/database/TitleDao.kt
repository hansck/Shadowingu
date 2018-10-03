package com.hansck.shadowingu.database

import android.arch.persistence.room.*
import com.hansck.shadowingu.model.Title
import io.reactivex.Maybe

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Dao
interface TitleDao {

	@Query("SELECT * FROM title")
	fun getAll(): Maybe<List<Title>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(titles: Array<Title>)

	@Update
	fun updateTitle(title: Title)
}