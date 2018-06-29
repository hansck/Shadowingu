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

        @ColumnInfo(name = "image")
        var image: String,

        @ColumnInfo(name = "price")
        var price: Int,

        @ColumnInfo(name = "unlock")
        var unlock: Boolean) {

    companion object {
        fun populateData(): Array<Avatar> {
            return arrayOf(
                    Avatar(0, "Avatar A", "This is description", "ic_person", 1, true),
                    Avatar(1, "Avatar B", "This is description", "ic_person", 2, true),
                    Avatar(2, "Avatar C", "This is description", "ic_person", 2, false))
        }
    }
}