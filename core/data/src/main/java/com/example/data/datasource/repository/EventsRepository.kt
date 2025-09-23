package com.example.data.datasource.repository

import android.util.Log
import com.example.data.datasource.dao.EventDao
import com.example.data.datasource.model.EventEntity
import com.example.data.datasource.model.toEventEntity
import com.example.network.data.EventsApi
import com.example.network.data.EventsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart

class EventsRepository(
    private val api: EventsApiService = EventsApi.retrofitService,
    private val eventDao: EventDao
) {

    fun getEventsFlow(): Flow<List<EventEntity>> = eventDao.getAllEvents()
        .onStart {
            refreshEventsIfNeeded()
            emitAll(eventDao.getAllEvents())
        }
        .catch { e -> throw e }


    // Função privada para atualizar o banco a partir da API.
    private suspend fun refreshEventsIfNeeded() {
        try {
            val response = api.getEvents()
            if (response.isSuccessful) {
                val apiDocuments = response.body()?.documents ?: emptyList()

                // Mapeando para EventEntity.
                val apiEvents = apiDocuments.map { it.toEventEntity() }

                // Verificar se houve alteração (simples comparando ids ou conteúdo)
                val localEvents = eventDao.getAllEvents().firstOrNull() ?: emptyList()
                if (apiEvents != localEvents) {
                    eventDao.insertEvents(apiEvents)
                }
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Erro ao atualizar eventos: ${e.message}")
            throw e
        }
    }



}