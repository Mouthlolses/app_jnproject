package com.example.app_jnproject.ui.screens.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_jnproject.connect.ConnectivityObserver
import com.example.data.datasource.model.EventEntity
import com.example.data.datasource.repository.EventsRepository
import com.example.network.model.Document
import com.example.network.model.EventFields
import com.example.network.model.FirestoreBoolean
import com.example.network.model.FirestoreString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: EventsRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected: StateFlow<Boolean> = connectivityObserver.isConnected

    val events: StateFlow<FetchEventsUiState> =
        repository.getEventsFlow()
            .map { list ->
                FetchEventsUiState(
                    isLoading = false,
                    events = list.map { it.toDocument() }, //transform document
                    error = null
                )
            }.catch { e ->
                emit(
                    FetchEventsUiState(
                        isLoading = false,
                        events = emptyList(),
                        error = "Sem conexão com a Internet! Vamos tentar novamente?"
                    )
                )
                Log.e("NewsViewModel", "Error collecting events: ${e.message}")
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                FetchEventsUiState(isLoading = true)
            )

    fun refreshEvents() {
        viewModelScope.launch {
            repository.refreshEventsManually()
        }
    }
}


private fun EventEntity.toDocument(): Document {
    return Document(
        name = this.id,
        fields = EventFields(
            title = FirestoreString(this.title),
            desc = FirestoreString(this.desc),
            date = FirestoreString(this.date),
            img = FirestoreString(this.img),
            location = FirestoreString(this.location),
            favorite = FirestoreBoolean(this.favorite)
        )
    )
}

data class FetchEventsUiState(
    val isLoading: Boolean = false,
    val events: List<Document> = emptyList(),
    val error: String? = null
)