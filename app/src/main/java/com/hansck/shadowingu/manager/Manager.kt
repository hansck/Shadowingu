package com.hansck.shadowingu.manager

import com.hansck.shadowingu.model.*

/**
 * Created by Hans CK on 13-Jun-18.
 */
class Manager {

    companion object {
        val instance = Manager

        lateinit var user: User
        var stages: ArrayList<Stage> = ArrayList()
        var audios: ArrayList<Audio> = ArrayList()
        var avatars: ArrayList<Avatar> = ArrayList()
        var titles: ArrayList<Title> = ArrayList()
        var badges: ArrayList<Badge> = ArrayList()
    }
}