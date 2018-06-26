package com.hansck.shadowingu.util

import android.content.SharedPreferences

/**
 * Created by Hans CK on 26-Jun-18.
 */
class PersistentManager {

    companion object {
        val instance = PersistentManager()
    }

    lateinit var keyStore: SharedPreferences

    fun clearKeyStore() {
        keyStore.edit().clear().apply()
    }

    fun setActiveAvatar(idAvatar: Int) {
        val editor = keyStore.edit()
        editor.putString("activeAvatar", idAvatar.toString())
        editor.apply()
    }

    fun getActiveAvatar(): Int {
        return keyStore.getString("activeAvatar", "1").toInt()
    }
}