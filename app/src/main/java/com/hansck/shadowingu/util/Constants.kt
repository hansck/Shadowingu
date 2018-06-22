package com.hansck.shadowingu.util

import android.Manifest

/**
 * Created by Hans CK on 10-Nov-17.
 */
class Constants {

    object Database {
        const val USER = "user"
        const val EMAIL = "email"
        const val LEVEL = "level"
    }

    object Permissions {
        val CAMERA = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    object General {
        const val PREFERENCE = "prefs"
        const val USERS = "users"
        const val CLAIMS = "claims"
        const val CYCLES = "cycles"
    }

    object User {
        const val USER_ID = "userID"
        const val EMAIL = "email"
        const val ID_TOKEN = "idToken"
        const val ACCESS_TOKEN = "accessToken"
        const val NAME = "name"
    }

    object DateFormat {
        const val DATE = "dd MMMM yyyy"
        const val DATE_FULL = "yyyy-MM-dd HH:mm:ss"
        const val DATETIME = "yyyyMMddHHmmss"
        const val FULL_SHORT = "EEE, d MMM yyyy"
        const val FULL_LONG = "EEEE, d MMMM yyyy"
    }

    object Service {
        const val ADD_PHOTO = "addPhoto"
    }

    object Connection {
        var BEARER = "bearer"
        var TIME_OUT = 5
        var TYPE = "type"
    }
}