package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 07-Jun-18.
 */
data class User(
        var idUser: String,
        var name: String,
        var level: Int,
        var exp: Int,
        var gem: Int,
        var image: String)