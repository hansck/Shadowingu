package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 07-Jun-18.
 */
data class Stage(
        var idStage: String,
        var category: String,
        var fastestTime: Long,
        var unlock: Boolean)