package com.caririfest.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.dao.RecentEventDao
import com.caririfest.data.datasource.model.EventEntity
import com.caririfest.data.datasource.model.RecentEventEntity

@Database(entities = [EventEntity::class, RecentEventEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun recentEventDao(): RecentEventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_database"
                ).build().also { INSTANCE = it }
            }
        }

    }
}