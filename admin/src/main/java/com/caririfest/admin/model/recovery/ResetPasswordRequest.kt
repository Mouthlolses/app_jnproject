package com.caririfest.admin.model.recovery

data class ResetPasswordRequest(
    val token: String,
    val newPassword: String
)
