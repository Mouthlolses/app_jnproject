package com.caririfest.app_jnproject.ui.screens.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.data.datasource.model.EventEntity
import com.caririfest.data.datasource.repository.EventsRepository
import com.caririfest.network.model.Document
import com.caririfest.network.model.EventFields
import com.caririfest.network.model.FirestoreBoolean
import com.caririfest.network.model.FirestoreString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    repository: EventsRepository,
) : ViewModel() {

    val events: StateFlow<FetchEventsUiState> =
        repository.getEventsFlow()
            .map { list ->
                FetchEventsUiState(
                    isLoading = false,
                    events = list.map { it.toDocument() },
                    error = null
                )
            }.catch { e ->
                emit(
                    FetchEventsUiState(
                        isLoading = false,
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
            place = FirestoreString(this.place),
            time = FirestoreString(this.time),
            favorite = FirestoreBoolean(this.favorite),
            link = FirestoreString(this.link)
        )
    )
}

data class FetchEventsUiState(
    val isLoading: Boolean = false,
    val events: List<Document> = emptyList(),
    val error: String? = null
)