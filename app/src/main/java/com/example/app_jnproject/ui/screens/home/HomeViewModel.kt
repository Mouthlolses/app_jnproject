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
                img = R.drawable.hortoimg1,
                url = "https://www.buskaza.com.br/blog/guia-de-cidades/juazeiro-do-norte-ceara-por-que-voce-vai-se-encantar-pela-capital-da-fe/",
            ),
            CityLocation(
                id = 2,
                name = "Crato",
                img = R.drawable.hortoimg1,
                url = "https://hortodopadrecicero.net.br/historia-2/",
            ),
            CityLocation(
                id = 3,
                name = "Barbalha",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 4,
                name = "Caririaçu",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 5,
                name = "Missão Velha",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 6,
                name = "Jardim",
                img = R.drawable.hortoimg1,
                url = "",
            ),
            CityLocation(
                id = 7,
                name = "Santana do Cariri",
                img = R.drawable.hortoimg1,
                url = ""
            ),
            CityLocation(
                id = 8,
                name = "Nova Olinda",
                img = R.drawable.hortoimg1,
                url = ""
            ),
            CityLocation(
                id = 9,
                name = "Farias Brito",
                img = R.drawable.hortoimg1,
                url = ""
            ),
        )
    }
}


data class CityLocation(
    val id: Int,
    val name: String,
    val url: String,
    @param:DrawableRes val img: Int
)

