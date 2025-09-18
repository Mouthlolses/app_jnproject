package com.example.app_jnproject.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.app_jnproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _cityLocation = MutableStateFlow<List<CityLocation>>(emptyList())
    val cityLocation: StateFlow<List<CityLocation>> = _cityLocation.asStateFlow()

    init {
        loadCards()
    }

    private fun loadCards() {
        _cityLocation.value = listOf(
            CityLocation(
                id = 1,
                name = "Juazeiro do Norte",
                location = "Juazeiro do Norte - CE",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                url = "https://www.buskaza.com.br/blog/guia-de-cidades/juazeiro-do-norte-ceara-por-que-voce-vai-se-encantar-pela-capital-da-fe/",
            ),
            CityLocation(
                id = 2,
                name = "Horto",
                location = "Juazeiro do Norte - CE",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                url = "https://hortodopadrecicero.net.br/historia-2/",
            ),
            CityLocation(
                id = 3,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 4,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 5,
                name = "Praça Padre Cicero",
                location = "Avenida Padre Cicero",
                date = "20/05/2025",
                img = R.drawable.hortoimg1,
                url = "",
            )
        )
    }
}


data class CityLocation(
    val id: Int,
    val name: String,
    val location: String,
    val date: String,
    val url: String,
    @param:DrawableRes val img: Int
)

