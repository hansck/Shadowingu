package com.hansck.shadowingu.util

import android.Manifest

/**
 * Created by Hans CK on 10-Nov-17.
 */
class Constants {

	object Database {
		const val USER = "user"
		const val LEVEL = "level"
		const val KEY = "key"
	}

	object Preferences {
		const val PREFERENCE = "preference"
		const val IS_LOGIN = "isLogin"
		const val ACTIVE_AVATAR = "activeAvatar"
		const val PERFECT_PLAY = "perfectPlay"
		const val FIRST_BUY = "firstBuy"
		const val FIRST_GAME_OVER = "firstGameOver"
		const val MAX_LEVEL = "maxLevel"
		const val ALL_STAGE_CLEAR = "allStageClear"
	}

	object General {
		const val FIRST_LEVEL = 0
		const val MAX_LEVEL = 5
		const val MAX_STAGE = 5
		const val MAX_LEADERBOARD = 20
	}

	object Permissions {
		val CAMERA = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
	}
}