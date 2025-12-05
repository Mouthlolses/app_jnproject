package com.caririfest.admin.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminCreateRequest(
    val adminName: String,
    val adminLastName: String,
    val docAdmin: String,
    val adminEmail: String,
    val adminEmailConfirm: String,
    val password: String
)
