package com.caririfest.admin.model.login

import com.caririfest.admin.model.register.AdminResponse

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val admin: AdminResponse
)