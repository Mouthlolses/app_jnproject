package com.example.app_jnproject.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.app_jnproject.R
import com.example.app_jnproject.data.CityLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _cityLocation = MutableStateFlow<List<CityLocation>>(emptyList())
    val cityLocation: StateFlow<List<CityLocation>> = _cityLocation

    init {
        loadCards()
    }

    private fun loadCards() {
        _cityLocation.value = listOf(
            CityLocation(
                id = 1,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                isFavorite = false
            ),
            CityLocation(
                id = 2,
                name = "Horto",
                location = "Horto",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                isFavorite = false
            ),
            CityLocation(
                id = 1,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                isFavorite = false
            ),
            CityLocation(
                id = 1,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                isFavorite = false
            ),
            CityLocation(
                id = 1,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                isFavorite = false
            ),
        )
    }
}
