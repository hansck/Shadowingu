package com.hansck.shadowingu.screen.shop

import android.content.Context
import com.hansck.shadowingu.model.Avatar
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.model.User
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.PersistentManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class ShopViewModel(var context: Context?) {

	lateinit var user: User
	lateinit var boughtAvatar: Avatar
	var isFirstBuy: Boolean = false
	var badges: ArrayList<Badge> = ArrayList()
	var avatars: ArrayList<Avatar> = ArrayList()

	fun setAvatars() {
		avatars = DataManager.instance.avatars
	}

	fun setData() {
		user = DataManager.instance.user
		badges = DataManager.instance.badges
	}

	fun buyAvatar(idAvatar: Int) {
		boughtAvatar = avatars[idAvatar]
		boughtAvatar.unlock = true
		user.gem -= boughtAvatar.price
		DataManager.instance.user = user
		checkBadge()
	}

	private fun checkBadge() {
		isFirstBuy = !PersistentManager.instance.isFirstBuy()
		if (isFirstBuy) {
			badges[2].unlock = true
			DataManager.instance.badges = badges
			PersistentManager.instance.setFirstBuy()
		}
	}

	fun setActiveAvatar(id: Int) {
		PersistentManager.instance.setActiveAvatar(id)
		user.image = avatars[id].unlockedImage
		DataManager.instance.user = user
	}
}