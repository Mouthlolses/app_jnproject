package com.example.app_jnproject.ui.screens.newscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.data.Document
import com.example.network.repository.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: EventsRepository = EventsRepository()
) : ViewModel() {

    private val _events = MutableStateFlow(FetchEventsUiState())
    val events: StateFlow<FetchEventsUiState> = _events.asStateFlow()


    fun fetchEvents() {
        viewModelScope.launch {
            _events.value = _events.value.copy(isLoading = true, error = null)
            try {
                repository.getEvents().collectLatest { resultList ->
                    _events.value = FetchEventsUiState(
                        isLoading = false,
                        events = resultList,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _events.value = _events.value.copy(
                    isLoading = false,
                    error = "Erro inesperado! Vamos tentar de novo?"
                )
            }
        }
    }
}

data class FetchEventsUiState(
    val isLoading: Boolean = false,
    val events: List<Document> = emptyList(),
    val error: String? = null
)