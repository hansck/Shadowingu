package com.hansck.shadowingu.screen.leaderboard

import android.content.Context
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LeaderboardViewModel(var context: Context?) {

	lateinit var currentUser: LeaderboardUser
	var users: ArrayList<LeaderboardUser> = ArrayList()

	fun setCurrentUser() {
		val user = DataManager.instance.user
		currentUser = LeaderboardUser(user.email, user.name, user.level, user.image, 0, DataManager.instance.getUnlockBadges())
	}

	fun setLeaderboard() {
		users = DataManager.instance.leaderboardUsers
	}
}