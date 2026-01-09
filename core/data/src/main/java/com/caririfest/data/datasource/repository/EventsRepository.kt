package com.caririfest.data.datasource.repository

import android.util.Log
import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.model.EventEntity
import com.caririfest.data.datasource.model.toEventEntity
import com.caririfest.network.data.EventsApi
import com.caririfest.network.data.EventsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart

class EventsRepository(
    private val api: EventsApiService = EventsApi.retrofitService,
    private val eventDao: EventDao
) {

    fun getEventsFlow(): Flow<List<EventEntity>> = eventDao.getAllEvents()
        .onStart {
            refreshEventsIfNeeded()
        }
        .catch { e ->
            throw e
        }


    private suspend fun refreshEventsIfNeeded() {
        try {
            val response = api.getEvents()
            if (response.isSuccessful) {
                val apiDocuments = response.body()?.documents ?: emptyList()

                val apiEvents = apiDocuments.map { it.toEventEntity() }
                val localEvents = eventDao.getAllEvents().firstOrNull() ?: emptyList()

                val apiIds = apiEvents.map { it.id }
                val localIds = localEvents.map { it.id }

                val removeIds = localIds - apiIds.toSet()

                if (removeIds.isNotEmpty()) {
                    eventDao.deleteEventByIds(removeIds)
                }

                eventDao.insertEvents(apiEvents)
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Erro ao atualizar eventos: ${e.message}")
            throw e
        }
    }

    suspend fun refreshEvents() = refreshEventsIfNeeded()
}