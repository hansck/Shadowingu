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

		@ColumnInfo(name = "high_score")
		var high_score: Int,

		@ColumnInfo(name = "exp")
		var exp: Int,

		@ColumnInfo(name = "lockedImage")
		var lockedImage: String,

		@ColumnInfo(name = "unlockedImage")
		var unlockedImage: String,

		@ColumnInfo(name = "lockedIcon")
		var lockedIcon: String,

		@ColumnInfo(name = "unlockedIcon")
		var unlockedIcon: String,

		@ColumnInfo(name = "cleared")
		var cleared: Boolean) {

	companion object {
		fun populateData(): Array<Lesson> {
			return arrayOf(
					Lesson(0, "Pronoun", "Beginner", 0, 0, 500,
							"bg_grey", "bg_purple", "ic_topic_home_disabled", "ic_topic_home", false),
					Lesson(1, "Things at School", "Beginner", 0, 0, 750,
							"bg_grey", "bg_teal", "ic_topic_school_1_disabled", "ic_topic_school_1", false),
					Lesson(2, "School Activities", "Beginner", 0, 0, 1000,
							"bg_grey", "bg_teal", "ic_topic_school_2_disabled", "ic_topic_school_2", false),
					Lesson(3, "At Canteen", "Beginner", 0, 0, 1250,
							"bg_grey", "bg_blue", "ic_topic_canteen_1_disabled", "ic_topic_canteen_1", false),
					Lesson(4, "At Canteen 2", "Beginner", 0, 0, 1500,
							"bg_grey", "bg_blue", "ic_topic_canteen_2_disabled", "ic_topic_canteen_2", false),
					Lesson(5, "Places", "Lower-Intermediate", 0, 0, 1750,
							"bg_grey", "bg_purple", "ic_topic_places_disabled", "ic_topic_places", false),
					Lesson(6, "Travelling", "Lower-Intermediate", 0, 0, 2000,
							"bg_grey", "bg_teal", "ic_topic_travelling_disabled", "ic_topic_travelling", false))
		}
	}
}