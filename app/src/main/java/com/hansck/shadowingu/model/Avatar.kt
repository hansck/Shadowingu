package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Avatar(
		@PrimaryKey
		var idAvatar: Int,

		@ColumnInfo(name = "name")
		var name: String,

		@ColumnInfo(name = "description")
		var description: String,

		@ColumnInfo(name = "lockedImage")
		var lockedImage: String,

		@ColumnInfo(name = "unlockedImage")
		var unlockedImage: String,

		@ColumnInfo(name = "price")
		var price: Int,

		@ColumnInfo(name = "heart")
		var hearts: Int,

		@ColumnInfo(name = "unlock")
		var unlock: Boolean) {

	companion object {
		fun populateData(): Array<Avatar> {
			return arrayOf(
					Avatar(0, "Satoshi", "Satoshi is a normal high school student. He afraid zombies, but he will try his best to help you.\nHe has 5 life points.",
							"ic_avatar_a_disabled", "ic_avatar_a", 1, 5, true),
					Avatar(1, "Taro", "Taro is an enthusiast learner. He will be glad to help you defeated the zombies troops.\nHe has 6 life points.",
							"ic_avatar_b_disabled", "ic_avatar_b", 2, 6, false),
					Avatar(2, "Kawakami", "Kawakami is a fighter type. He is more than love to help you destroy all zombies that stand in your way.\nHe has 7 life points.",
							"ic_avatar_c_disabled", "ic_avatar_c", 2, 7, false))
		}
	}
}