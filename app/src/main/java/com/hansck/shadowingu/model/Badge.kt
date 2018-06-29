package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Badge(
        @PrimaryKey
        var idBadge: Int = 0,

        @ColumnInfo(name = "name")
        var name: String = "",

        @ColumnInfo(name = "description")
        var description: String = "",

        @ColumnInfo(name = "image")
        var image: String = "",

        @ColumnInfo(name = "unlock")
        var unlock: Boolean = false) {

    companion object {
        fun populateData(): Array<Badge> {
            return arrayOf(
                    Badge(0, "Perfecto", "Finish a stage without a single fail.", "ic_person", false),
                    Badge(1, "Completionist", "Finish all stages.", "ic_person", false),
                    Badge(2, "Rich Buyer!", "Buy your first avatar.", "ic_person", false),
                    Badge(3, "Maximus", "Has reach maximum level.", "ic_person", false),
                    Badge(4, "Don't Give Up", "Game over for the first time.", "ic_person", false))
        }
    }
}