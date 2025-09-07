package com.example.network.repository

import android.util.Log
import com.example.network.data.Document
import com.example.network.data.EventsApi
import com.example.network.data.EventsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventsRepository(
    private val api: EventsApiService = EventsApi.retrofitService
) {
    fun getEvents(): Flow<List<Document>> = flow {
        val response = api.getEvents()
        try {
            emit(response.body()?.documents ?: emptyList())
        } catch (e: Exception) {
            Log.e("callError", "Error : ${e.message}")
        }
    }
}