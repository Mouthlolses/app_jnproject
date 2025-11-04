package com.caririfest.data.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caririfest.data.datasource.model.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id IN (:ids)")
    suspend fun getEventById(ids: List<String>): List<EventEntity>

    @Query("DELETE FROM events WHERE id IN (:ids)")
    suspend fun deleteEventByIds(ids: List<String>)

}