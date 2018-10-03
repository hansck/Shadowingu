package com.hansck.shadowingu.util

import com.hansck.shadowingu.model.*

/**
 * Created by Hans CK on 13-Jun-18.
 */
class DataManager {

	companion object {
		val instance = DataManager()
	}

	lateinit var user: User
	var topics: ArrayList<Topic> = ArrayList()
	var words: ArrayList<Word> = ArrayList()
	var avatars: ArrayList<Avatar> = ArrayList()
	var titles: ArrayList<Title> = ArrayList()
	var badges: ArrayList<Badge> = ArrayList()
	var levels: ArrayList<Level> = ArrayList()
	var leaderboardUsers: ArrayList<LeaderboardUser> = ArrayList()

	fun addStages(list: List<Topic>) {
		topics.clear()
		topics.addAll(list)
		topics[0].cleared = true
		topics[1].cleared = true
		topics[2].cleared = true
		topics[3].cleared = true
		topics[4].cleared = true
		topics[5].cleared = true
		topics[6].cleared = true
	}

	fun addWords(list: List<Word>) {
		words.clear()
		words.addAll(list)
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

	fun addLevels(list: List<Level>) {
		levels.clear()
		levels.addAll(list)
	}

	fun addLeaderboardUser(user: LeaderboardUser) {
		leaderboardUsers.add(user)
	}

	fun getWordById(idWord: Int): Word {
		return words.first { it.idWord == idWord }
	}

	fun getWordsByStage(idStage: Int): List<Word> {
		return words.filter { it.stage == idStage }
	}

	fun getUnclearLevel(): Int {
		return topics.filter { it.cleared }.size
	}

	fun getUnlockBadges(): List<Badge> {
		return badges.filter { it.unlock }
	}

	fun getActiveAvatar(): Avatar {
		return avatars.first { it.idAvatar == PersistentManager.instance.getActiveAvatar() }
	}
}