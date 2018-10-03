package com.hansck.shadowingu.screen.achievement

import android.content.Context
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class AchievementViewModel(var context: Context?) {

	var badges: ArrayList<Badge> = ArrayList()

	fun setBadges() {
		badges = DataManager.instance.badges
	}
}