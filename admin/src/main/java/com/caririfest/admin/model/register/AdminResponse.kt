package com.caririfest.admin.model.register

data class AdminResponse(
    val id: Long,
    val adminName: String,
    val adminLastName: String,
    val docAdmin: String,
    val adminEmail: String
)