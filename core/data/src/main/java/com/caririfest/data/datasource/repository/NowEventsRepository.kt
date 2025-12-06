package com.caririfest.data.datasource.repository

import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.model.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class NowEventsRepository @Inject constructor(
    private val eventDao: EventDao,
) {

    private fun getTodayString(): String {
        val today = LocalDate.now()
        return today.toString()
    }

    fun getTodayEvents(): Flow<List<EventEntity>> {
        val today = getTodayString()
        return eventDao.getAllEvents().map { list ->
            list.filter { it.date == today }
        }
    }

}