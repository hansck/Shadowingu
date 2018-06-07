package com.hansck.shadowingu.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


/**
 * Created by Hans CK on 31-Oct-17.
 */
class BaseApplication : Application() {

    var connected = false

    companion object {
        var instance: BaseApplication? = null

        val getInstance: BaseApplication
            get() {
                if (instance == null) {
                    synchronized(BaseApplication::class.java) {
                        if (instance == null) {
                            instance = BaseApplication()
                        }
                    }
                }
                return instance!!
            }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isConnected(): Boolean {
        val cm = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return connected
    }
}