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
        var idTitle: Long,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "minLevel")
        var minLevel: Int) {

    fun populateData(): Array<Title> {
        return arrayOf(
                Title(1, "Apprentice", 0),
                Title(2, "Warrior", 3),
                Title(3, "Knight", 5))
    }
}