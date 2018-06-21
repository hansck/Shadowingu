package com.hansck.shadowingu.util

import com.hansck.shadowingu.model.*

/**
 * Created by Hans CK on 13-Jun-18.
 */
class Manager {

    companion object {
        val instance = Manager()
    }

    lateinit var user: User
    var stages: ArrayList<Stage> = ArrayList()
    var audios: ArrayList<Audio> = ArrayList()
    var avatars: ArrayList<Avatar> = ArrayList()
    var titles: ArrayList<Title> = ArrayList()
    var badges: ArrayList<Badge> = ArrayList()

    fun addStages(list: List<Stage>) {
        stages.clear()
        stages.addAll(list)
    }

    fun addAudios(list: List<Audio>) {
        audios.clear()
        audios.addAll(list)
    }

    fun addAvatars(list: List<Avatar>) {
        avatars.clear()
        avatars.addAll(list)
    }

    fun addTitles(list: List<Title>) {
        titles.clear()
        titles.addAll(list)
    }

    fun addBadges(list: List<Badge>) {
        badges.clear()
        badges.addAll(list)
    }

    fun getActiveTitle(): Title {
        lateinit var activeTitle: Title
        for (title in titles) {
            if (title.minLevel <= user.level) {
                activeTitle = title
            }
        }
        return activeTitle
    }
}