package com.caririfest.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caririfest.data.datasource.repository.NowEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NowEventViewModel @Inject constructor(
    private val repository: NowEventsRepository
) : ViewModel() {

    val todayEvents = repository.getTodayEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}