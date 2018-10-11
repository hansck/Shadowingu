package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Lesson(
		@PrimaryKey
		var idLesson: Int,

		@ColumnInfo(name = "category")
		var category: String,

		@ColumnInfo(name = "fastestTime")
		var fastestTime: Long,

		@ColumnInfo(name = "exp")
		var exp: Int,

		@ColumnInfo(name = "lockedImage")
		var lockedImage: String,

		@ColumnInfo(name = "unlockedImage")
		var unlockedImage: String,

		@ColumnInfo(name = "cleared")
		var cleared: Boolean) {

	companion object {
		fun populateData(): Array<Lesson> {
			return arrayOf(
					Lesson(0, "Beginner", 0, 500, "ic_default_image", "ic_person", false),
					Lesson(1, "Beginner", 0, 750, "ic_default_image", "ic_person", false),
					Lesson(2, "Beginner", 0, 1000, "ic_default_image", "ic_person", false),
					Lesson(3, "Beginner", 0, 1250, "ic_default_image", "ic_person", false),
					Lesson(4, "Beginner", 0, 1500, "ic_default_image", "ic_person", false),
					Lesson(5, "Lower-Intermediate", 0, 1750, "ic_default_image", "ic_person", false),
					Lesson(6, "Lower-Intermediate", 0, 2000, "ic_default_image", "ic_person", false))
		}
	}
}