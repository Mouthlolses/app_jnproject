package com.caririfest.admin.model.register

data class AdminCreateRequest(
    val adminName: String,
    val adminLastName: String,
    val docAdmin: String,
    val adminEmail: String,
    val adminEmailConfirm: String,
    val password: String
)
