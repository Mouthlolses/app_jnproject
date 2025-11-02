package com.caririfest.app_jnproject.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.caririfest.app_jnproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _cityLocation = MutableStateFlow<List<CityLocation>>(emptyList())
    val cityLocation: StateFlow<List<CityLocation>> = _cityLocation.asStateFlow()

    private val _categories = MutableStateFlow<List<Categories>>(emptyList())
    val categories: StateFlow<List<Categories>> = _categories.asStateFlow()

    init {
        loadCards()
        loadCategories()
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


    private fun loadCategories() {
        _categories.value = listOf(
            Categories(
                id = 1,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 2,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 3,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 4,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 5,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 6,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 7,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            ),
            Categories(
                id = 8,
                image = R.drawable.caririfestlogo1,
                nameCategories = "Random"
            )
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

data class Categories(
    val id: Int,
    @param:DrawableRes val image: Int,
    val nameCategories: String = ""
)