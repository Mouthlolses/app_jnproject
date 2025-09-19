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
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 2,
                name = "Crato",
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 3,
                name = "Barbalha",
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 4,
                name = "Em Breve",
                futureImpl = "Caririaçu",
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 5,
                name = "Em Breve",
                futureImpl = "Missão Velha",
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 6,
                name = "Em Breve",
                futureImpl = "Jardim",
                img = R.drawable.caririfestlogo1,
                url = "",
            ),
            CityLocation(
                id = 7,
                name = "Em Breve",
                futureImpl = "Santana do Cariri",
                img = R.drawable.caririfestlogo1,
                url = ""
            ),
            CityLocation(
                id = 8,
                name = "Em Breve",
                futureImpl = "Nova Olinda",
                img = R.drawable.caririfestlogo1,
                url = ""
            ),
            CityLocation(
                id = 9,
                name = "Em Breve",
                futureImpl = "Farias Brito",
                img = R.drawable.caririfestlogo1,
                url = ""
            ),
        )
    }
}


data class CityLocation(
    val id: Int,
    val name: String,
    val futureImpl: String = "",
    val url: String,
    @param:DrawableRes val img: Int
)

