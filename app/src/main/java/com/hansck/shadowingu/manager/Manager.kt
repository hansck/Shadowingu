package com.hansck.shadowingu.manager

import com.hansck.shadowingu.model.*

/**
 * Created by Hans CK on 13-Jun-18.
 */
class Manager {

    lateinit var user: User
    lateinit var stages: ArrayList<Stage>
    lateinit var audios: ArrayList<Audio>
    lateinit var avatars: ArrayList<Avatar>
    lateinit var titles: ArrayList<Title>
    lateinit var badges: ArrayList<Badge>

    companion object {
        val instance = Manager
    }
}