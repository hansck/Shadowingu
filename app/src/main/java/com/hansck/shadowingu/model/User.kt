package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class User(
		@PrimaryKey
		var idUser: Int,

		@ColumnInfo(name = "email")
		var email: String,

		@ColumnInfo(name = "name")
		var name: String,

		@ColumnInfo(name = "level")
		var level: Int,

		@ColumnInfo(name = "exp")
		var exp: Int,

		@ColumnInfo(name = "gem")
		var gem: Int,

		@ColumnInfo(name = "image")
		var image: String) {

	companion object {
		fun populateData(email: String, name: String): User {
			return User(0, email, name, 1, 0, 0, "ic_person")
		}
	}
}