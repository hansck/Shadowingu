package com.hansck.shadowingu.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Hans CK on 07-Jun-18.
 */
@Entity
data class User(
        @PrimaryKey(autoGenerate = true)
        var idUser: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "level")
        var level: Int,

        @ColumnInfo(name = "exp")
        var exp: Int,

        @ColumnInfo(name = "gem")
        var gem: Int,

        @ColumnInfo(name = "image")
        var image: String)