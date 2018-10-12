package com.hansck.shadowingu.model

/**
 * Created by Hans CK on 22-Jun-18.
 */
data class LeaderboardUser(
		var email: String = "",
		var name: String = "",
		var level: Int = 0,
		var image: String = "",
		var badges: List<Badge> = ArrayList(),
		var lessons_lite: Int = 0,
		var lessons_gamified: Int = 0)