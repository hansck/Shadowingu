package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 07-Jun-18.
 */
data class Badge(
        var idBadge: String,
        var name: String,
        var description: String,
        var image: String,
        var unlock: Boolean)