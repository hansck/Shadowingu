package com.hansck.shadowingu.presentation

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import com.hansck.shadowingu.dao.AppDatabase


/**
 * Created by Hans CK on 31-Oct-17.
 */
class App : Application() {

    var connected = false

    companion object {
        var instance: App? = null
        var database: AppDatabase? = null

        val getInstance: App
            get() {
                if (instance == null) {
                    synchronized(App::class.java) {
                        if (instance == null) {
                            instance = App()
                        }
                    }
                }
                return instance!!
            }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database =  Room.databaseBuilder(this, AppDatabase::class.java, "shadowingu-db").build()
    }

    fun isConnected(): Boolean {
        val cm = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return connected
    }
}