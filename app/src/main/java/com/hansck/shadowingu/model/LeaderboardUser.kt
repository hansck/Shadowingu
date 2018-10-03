package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 22-Jun-18.
 */
data class LeaderboardUser(
		var email: String = "",
		var name: String = "",
		var level: Int = 0,
		var image: String = "",
		var rank: Int = 0,
		var badges: List<Badge> = ArrayList())