package com.example.app_jnproject.ui.screens.offers

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.app_jnproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OfferViewModel() : ViewModel() {

    val _uiState = MutableStateFlow<List<OffersData>>(emptyList())
    val uiState: StateFlow<List<OffersData>> = _uiState.asStateFlow()

    init {
        loadOffers()
    }

    private fun loadOffers() {
        _uiState.value = listOf(
            OffersData(
                id = 1,
                img = R.drawable.magazineluizalogo,
                titleOffer = "Ofertas Magazine Luiza",
                cuponOffer = "Cupom de Oferta: XXXXXXX"
            ),
            OffersData(
                id = 2,
                img = R.drawable.amazonlogo,
                titleOffer = "Ofertas Amazon",
                cuponOffer = "Cupom de Oferta: XXXXXX"
            ),
            OffersData(
                id = 3,
                img = R.drawable.logoamericanas,
                titleOffer = "Ofertas Americanas",
                cuponOffer = "Cupom de Oferta: XXXXX"
            ),
            OffersData(
                id = 4,
                img = R.drawable.logoshopee2,
                titleOffer = "Ofertas Shopee",
                cuponOffer = "Cupom de Oferta: XXXXX"
            )
        )
    }

}


data class OffersData(
    val id: Int = 0,
    @param:DrawableRes val img: Int = 0,
    val titleOffer: String = "",
    val cuponOffer: String = ""
)
