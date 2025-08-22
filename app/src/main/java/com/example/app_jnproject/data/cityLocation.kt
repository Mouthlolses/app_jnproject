package com.example.app_jnproject.data

import androidx.annotation.DrawableRes

data class CityLocation(
    val name: String = "",
    val location: String = "",
    val date: String = "",
    @param:DrawableRes val img: Int = 0,
)
