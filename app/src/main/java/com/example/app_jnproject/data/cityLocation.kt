package com.example.app_jnproject.data

import androidx.annotation.DrawableRes

data class CityLocation(
    val id: Int,
    val name: String,
    val location: String,
    val date: String,
    val url: String,
    val isFavorite: Boolean,
    @param:DrawableRes val img: Int
)
