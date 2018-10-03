package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Title(
		@PrimaryKey
		var idTitle: Int,

		@ColumnInfo(name = "name")
		var name: String,

		@ColumnInfo(name = "minLevel")
		var minLevel: Int) {

	companion object {
		fun populateData(): Array<Title> {
			return arrayOf(
					Title(0, "Kindergarten", 0),
					Title(1, "Elementary Student", 3),
					Title(2, "High School Student", 5))
		}
	}
}