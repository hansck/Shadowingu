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

		@ColumnInfo(name = "topic")
		var topic: String,

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
					Lesson(0, "Pronoun", "Beginner", 0, 500, "ic_default_image", "ic_person", false),
					Lesson(1, "Things at School", "Beginner", 0, 750, "ic_default_image", "ic_person", false),
					Lesson(2, "School Activities", "Beginner", 0, 1000, "ic_default_image", "ic_person", false),
					Lesson(3, "At Canteen", "Beginner", 0, 1250, "ic_default_image", "ic_person", false),
					Lesson(4, "At Canteen 2", "Beginner", 0, 1500, "ic_default_image", "ic_person", false),
					Lesson(5, "Places", "Lower-Intermediate", 0, 1750, "ic_default_image", "ic_person", false),
					Lesson(6, "Travelling", "Lower-Intermediate", 0, 2000, "ic_default_image", "ic_person", false))
		}
	}
}