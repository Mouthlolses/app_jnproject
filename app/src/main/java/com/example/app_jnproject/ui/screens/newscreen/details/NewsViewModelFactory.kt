package com.example.app_jnproject.ui.screens.newscreen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_jnproject.ui.screens.newscreen.NewsViewModel
import com.example.data.datasource.repository.EventsRepository

class NewsViewModelFactory(
    private val repository: EventsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}