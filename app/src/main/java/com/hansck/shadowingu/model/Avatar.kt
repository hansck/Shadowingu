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
        var idAvatar: Long,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "image")
        var image: String,

        @ColumnInfo(name = "price")
        var price: Int,

        @ColumnInfo(name = "unlock")
        var unlock: Boolean) {

    companion object {
        fun populateData(): Array<Avatar> {
            return arrayOf(
                    Avatar(1, "Avatar A", "", 1, false),
                    Avatar(2, "Avatar B", "", 2, false),
                    Avatar(3, "Avatar C", "", 2, false))
        }
    }
}