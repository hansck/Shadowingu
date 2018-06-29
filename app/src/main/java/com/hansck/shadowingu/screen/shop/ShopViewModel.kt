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
        checkBadge()
    }

    private fun checkBadge() {
        isFirstBuy = PersistentManager.instance.isFirstBuy()
        if (!isFirstBuy) PersistentManager.instance.setFirstBuy()
    }
}