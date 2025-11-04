package com.caririfest.data.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_events")
data class RecentEventEntity(
    @PrimaryKey val eventId: String,
    val vieweAt: Long = System.currentTimeMillis()
)
