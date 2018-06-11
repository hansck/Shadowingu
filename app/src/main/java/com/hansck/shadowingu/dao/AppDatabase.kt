package com.hansck.shadowingu.dao

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.hansck.shadowingu.model.Audio

/**
 * Created by Hans CK on 11-Jun-18.
 */
@Database(entities = arrayOf(Audio::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun audioDao(): AudioDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "Shadowingu.db")
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    // do something after database has been created
                                }

                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    // do something every time database is open
                                }
                            })
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}