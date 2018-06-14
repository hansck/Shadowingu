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
        var idBadge: Long,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "description")
        var description: String,

        @ColumnInfo(name = "image")
        var image: String,

        @ColumnInfo(name = "unlock")
        var unlock: Boolean) {

    companion object {
        fun populateData(): Array<Badge> {
            return arrayOf(
                    Badge(1, "Perfecto", "Finish a stage without a single fail.", "", false),
                    Badge(2, "Completionist", "Finish all stages.", "", false),
                    Badge(3, "Rich Buyer!", "Buy your first avatar.", "", false),
                    Badge(4, "Maximus", "Has reach maximum level.", "", false),
                    Badge(5, "Don't Give Up", "Game over for the first time.", "", false))
        }
    }
}