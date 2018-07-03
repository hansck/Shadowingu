package com.hansck.shadowingu.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.hansck.shadowingu.model.*
import java.util.concurrent.Executors


/**
 * Created by Hans CK on 11-Jun-18.
 */
@Database(entities = [(User::class), (Word::class), (Stage::class), (Avatar::class), (Title::class), (Badge::class), (Level::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun stageDao(): StageDao
    abstract fun audioDao(): WordDao
    abstract fun avatarDao(): AvatarDao
    abstract fun titleDao(): TitleDao
    abstract fun badgeDao(): BadgeDao
    abstract fun levelDao(): LevelDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "Shadowingu.db")
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    // do something after database has been created
                                    Log.e("DB", "MASUK CREATED")
                                    Executors.newSingleThreadScheduledExecutor().execute {
                                        getInstance(context)?.stageDao()?.insertAll(Stage.populateData())
                                        getInstance(context)?.audioDao()?.insertAll(Word.populateData())
                                        getInstance(context)?.avatarDao()?.insertAll(Avatar.populateData())
                                        getInstance(context)?.titleDao()?.insertAll(Title.populateData())
                                        getInstance(context)?.badgeDao()?.insertAll(Badge.populateData())
                                        getInstance(context)?.levelDao()?.insertAll(Level.populateData())
                                    }
                                }

                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    // do something every time database is open
                                    Log.e("DB", "MASUK OPEN")
                                    val currentDBPath = context.getDatabasePath("Shadowingu.db").absolutePath
                                    Log.e("DB", "NIH HASILNYA : $currentDBPath")
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