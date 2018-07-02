package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class Level(
        @PrimaryKey
        var idLevel: Int,

        @ColumnInfo(name = "exp")
        var exp: Int) {

    companion object {
        fun populateData(): Array<Level> {
            return arrayOf(
                    Level(0, 500),
                    Level(1, 1000),
                    Level(2, 1500),
                    Level(3, 2000),
                    Level(4, 2500))
        }
    }
}