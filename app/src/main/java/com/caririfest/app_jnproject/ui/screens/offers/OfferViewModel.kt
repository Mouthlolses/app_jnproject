package com.caririfest.app_jnproject.ui.screens.offers

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.caririfest.app_jnproject.R
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
                cuponOffer = "Cupons de oferta:",
                link = "https://www.magazinevoce.com.br/magazineprecobompromocao/",
                tags = listOf("100PRECOBOMPROMOCAO"),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 2,
                img = R.drawable.amazonlogo,
                titleOffer = "Ofertas Amazon",
                cuponOffer = "Cupons de oferta:",
                link = "https://amzn.to/46roRUQ",
                tags = listOf("",""),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 3,
                img = R.drawable.logo_mercadolivre,
                titleOffer = "Ofertas Mercado Livre",
                cuponOffer = "Cupons de oferta:",
                link = "https://mercadolivre.com/sec/2qYUXYR",
                tags = listOf("",""),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 4,
                img = R.drawable.sorte_logo,
                titleOffer = "Ofertas BoaSorte Online",
                cuponOffer = "Cupons de oferta:",
                link = "https://tidd.ly/46r3pz2",
                qrCode = R.drawable.awin_qrcode,
                tags = listOf("",""),
                tagColor = Color(0xFF4CAF50)
            ),
            OffersData(
                id = 5,
                img = R.drawable.logoshopee2,
                titleOffer = "Ofertas Shopee",
                cuponOffer = "Cupons de oferta:",
                link = "https://s.shopee.com.br/5AjB41ziAU",
                tags = listOf("",""),
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
    val qrCode: Int? = null,
    val tagIcon: Int = R.drawable.icon_cupon,
    val tags: List<String> = emptyList(),
    val tagColor: Color = Color(0xFF4CAF50)
)