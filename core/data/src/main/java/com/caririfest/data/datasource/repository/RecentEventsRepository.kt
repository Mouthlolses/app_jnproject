package com.caririfest.data.datasource.repository

import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.dao.RecentEventDao
import com.caririfest.data.datasource.model.EventEntity
import com.caririfest.data.datasource.model.RecentEventEntity
import javax.inject.Inject



class RecentEventsRepository @Inject constructor(
    private val eventDao: EventDao,
    private val recentEventDao: RecentEventDao
) {

    suspend fun markAsViewed(event: EventEntity) {
        recentEventDao.addRecentEvent(
            RecentEventEntity(eventId = event.id)
        )
    }

    suspend fun getRecentlyViewedEvents(limit: Int = 20): List<EventEntity> {
        val recent = recentEventDao.getRecentEvents(limit)
        val ids = recent.map { it.eventId }

        return eventDao.getEventById(ids)
            .sortedByDescending { e -> recent.indexOfFirst { it.eventId == e.id } }
    }

}