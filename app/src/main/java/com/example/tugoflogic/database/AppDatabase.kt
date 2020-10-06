package com.example.tugoflogic.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tugoflogic.model.*

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(
    entities = arrayOf(
        User::class,
        Bout::class,
        Facilitator::class,
        Game::class,
        MainClaim::class,
        Player::class,
        Rip::class,
        Vote::class,
        VoteType::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tug_of_logic_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}