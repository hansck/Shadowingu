package com.hansck.shadowingu.screen.leaderboard

import android.content.Context
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LeaderboardViewModel(var context: Context?) {

    var users: ArrayList<LeaderboardUser> = ArrayList()

    fun setLeaderboard() {
        users = DataManager.instance.leaderboardUsers
    }
}