package com.caririfest.app_jnproject.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.data.datasource.model.EventEntity
import com.caririfest.data.datasource.repository.RecentEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecentEventViewModel @Inject constructor(
    private val repository: RecentEventsRepository
) : ViewModel() {

    private val _recentEvents = MutableStateFlow<List<EventEntity>>(emptyList())
    val recentEvents: MutableStateFlow<List<EventEntity>> = _recentEvents

    fun loadEvents() {
        viewModelScope.launch {
            _recentEvents.value = repository.getRecentlyViewedEvents()
        }
    }

    fun onEventOpened(event: EventEntity) {
        viewModelScope.launch {
            repository.markAsViewed(event)
        }
    }

}
