package com.caririfest.app_jnproject.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mapbox.search.ResponseInfo
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchOptions
import com.mapbox.search.SearchSelectionCallback
import com.mapbox.search.SearchSuggestionsCallback
import com.mapbox.search.result.SearchResult
import com.mapbox.search.result.SearchSuggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchEngine: SearchEngine
): ViewModel() {

    private val _suggestions = MutableStateFlow<List<SearchSuggestion>>(emptyList())
    val suggestions: StateFlow<List<SearchSuggestion>> = _suggestions

    private val _selectedPlace = MutableStateFlow<SearchResult?>(null)
    val selectedPlace: StateFlow<SearchResult?> = _selectedPlace


    fun search(query: String) {
        if (query.length < 3) return   // evita requisições desnecessárias

        val options = SearchOptions(limit = 5)

        searchEngine.search(query, options, object : SearchSuggestionsCallback {
            override fun onSuggestions(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) {
                _suggestions.value = suggestions
            }
            override fun onError(e: Exception) {
                // TODO: tratar erro
            }
        })
    }

    fun selectSuggestion(suggestion: SearchSuggestion) {
        searchEngine.select(suggestion, object : SearchSelectionCallback {

            override fun onResult(
                suggestion: SearchSuggestion,
                result: SearchResult,
                responseInfo: ResponseInfo
            ) {
                _selectedPlace.value = result
            }

            override fun onResults(
                suggestion: SearchSuggestion,
                results: List<SearchResult>,
                responseInfo: ResponseInfo
            ) {
                // pega o primeiro se existir
                if (results.isNotEmpty()) {
                    _selectedPlace.value = results.first()
                }
            }

            override fun onError(e: Exception) {
                // você pode logar o erro
                Log.e("MapSearch", "Erro na busca", e)
            }

            // ignoramos sugestões extras
            override fun onSuggestions(
                suggestions: List<SearchSuggestion>,
                responseInfo: ResponseInfo
            ) = Unit
        })
    }
}