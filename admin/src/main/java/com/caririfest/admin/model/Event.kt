package com.caririfest.admin.model

data class Event(
    val date: String = "",
    val desc: String = "",
    val favorite: Boolean = false,
    val hot: Boolean = false,
    val img: String = "",
    val link: String = "",
    val location: String = "",
    val place: String = "",
    val time: String = "",
    val title: String = ""
)
