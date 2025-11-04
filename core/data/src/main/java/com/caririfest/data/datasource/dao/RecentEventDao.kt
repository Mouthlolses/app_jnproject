package com.caririfest.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caririfest.data.datasource.model.RecentEventEntity

@Dao
interface RecentEventDao {

    @Query("SELECT * FROM recent_events ORDER BY vieweAt DESC LIMIT :limit")
    suspend fun getRecentEvents(limit: Int = 20): List<RecentEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecentEvent(event: RecentEventEntity)

    @Query("DELETE FROM recent_events WHERE eventId = :eventId")
    suspend fun removeRecentEvent(eventId: String)

    @Query("DELETE FROM recent_events")
    suspend fun clearAll()
}