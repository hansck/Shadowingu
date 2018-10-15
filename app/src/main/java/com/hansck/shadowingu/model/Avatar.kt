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
					Avatar(0, "Satoshi", "Satoshi is a normal high school student. He has 5 hearts point.",
							"ic_avatar_a_disabled", "ic_avatar_a", 1, 5, true),
					Avatar(1, "Taro", "Taro is an enthusiast learner. He has 6 hearts point.",
							"ic_avatar_b_disabled", "ic_avatar_b", 2, 6, true),
					Avatar(2, "Kawakami", "Kawakami is a fighter type. He has 7 hearts point.",
							"ic_avatar_c_disabled", "ic_avatar_c", 2, 7, false))
		}
	}
}