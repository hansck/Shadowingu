package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 07-Jun-18.
 */
data class Avatar(
        var idAvatar: String,
        var name: String,
        var image: String,
        var price: Int,
        var unlock: Boolean)