package com.example.app_jnproject.ui.screens.offers

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
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
                tagIcon = R.drawable.ic_action_sell,
                img = R.drawable.magazineluizalogo,
                titleOffer = "Ofertas Magazine Luiza",
                cuponOffer = "Cupons de oferta:",
                link = "https://www.magazinevoce.com.br/magazineprecobompromocao/",
                tags = listOf("Tag1", "Tag2"),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 2,
                img = R.drawable.amazonlogo,
                titleOffer = "Ofertas Amazon",
                cuponOffer = "Cupons de oferta:",
                link = "https://amzn.to/46roRUQ",
                tags = listOf("Tag1", "Tag2"),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 3,
                img = R.drawable.logoshopee2,
                titleOffer = "Ofertas Shopee",
                cuponOffer = "Cupons de oferta:",
            ),
            OffersData(
                id = 4,
                tagIcon = R.drawable.ic_action_sell,
                img = R.drawable.sorte_logo,
                titleOffer = "Ofertas BoaSorte Online",
                cuponOffer = "Cupons de oferta:",
                link = "https://tidd.ly/46r3pz2",
                tags = listOf("Tag1", "Tag2"),
                tagColor = Color(0xFF4CAF50)
            )
        )
    }
}


data class OffersData(
    val id: Int = 0,
    @param:DrawableRes val img: Int = 0,
    val titleOffer: String = "",
    val cuponOffer: String = "",
    val link: String = "",
    val qrCode: String = "",
    @param:DrawableRes val tagIcon: Int = R.drawable.ic_action_sell,
    val tags: List<String> = emptyList(),
    val tagColor: Color = Color(0xFF4CAF50)
)
