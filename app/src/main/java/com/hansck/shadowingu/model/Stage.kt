package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Stage(
        @PrimaryKey
        var idStage: Int,

        @ColumnInfo(name = "category")
        var category: String,

        @ColumnInfo(name = "fastestTime")
        var fastestTime: Long,

        @ColumnInfo(name = "unlock")
        var unlock: Boolean) {

    companion object {
        fun populateData(): Array<Stage> {
            return arrayOf(
                    Stage(1, "School", 0, true),
                    Stage(2, "School", 0, false),
                    Stage(3, "School", 0, false),
                    Stage(4, "Canteen", 0, false),
                    Stage(5, "Canteen", 0, false))
        }
    }
}