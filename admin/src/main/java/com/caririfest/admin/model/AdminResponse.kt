package com.caririfest.admin.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminResponse(
    val id: Long,
    val adminName: String,
    val adminLastName: String,
    val docAdmin: String,
    val adminEmail: String
)