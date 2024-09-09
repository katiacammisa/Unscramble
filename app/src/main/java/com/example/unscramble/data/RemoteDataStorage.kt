package com.example.unscramble.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friend::class], version = 1)
abstract class UnscrambleDatabase : RoomDatabase() {
    abstract fun friendDao(): FriendDao

    companion object {
        @Volatile
        private var INSTANCE: UnscrambleDatabase? = null

        fun getDatabase(context: Context): UnscrambleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnscrambleDatabase::class.java,
                    "unscramble_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}