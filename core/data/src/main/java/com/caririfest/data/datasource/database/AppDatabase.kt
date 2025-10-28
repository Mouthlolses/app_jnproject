package com.caririfest.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.migrations.MIGRATION_2_3
import com.caririfest.data.datasource.model.EventEntity

@Database(entities = [EventEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_database"
                ).addMigrations(MIGRATION_2_3).build().also { INSTANCE = it }
            }
        }

    }
}