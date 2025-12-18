package com.caririfest.admin.repository

import com.caririfest.admin.exceptions.authExceptions.AuthException
import com.caririfest.admin.extensions.errorMessage
import com.caririfest.admin.model.login.LoginRequest
import com.caririfest.admin.model.login.LoginResponse
import com.caririfest.admin.model.recovery.ForgotPasswordRequest
import com.caririfest.admin.model.recovery.MessageResponse
import com.caririfest.admin.model.recovery.ResetPasswordRequest
import com.caririfest.admin.model.register.AdminCreateRequest
import com.caririfest.admin.model.register.AdminResponse
import com.caririfest.admin.network.AdminsApiService

class AdminsRepository(
    private val api: AdminsApiService
) {
    suspend fun createAdmin(request: AdminCreateRequest): AdminResponse {
        val response = api.createAdmin(request)
        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Resposta vazia no servidor")
        } else {
            throw Exception("Erro HTTP: ${response.code()} - ${response.message()}")
        }
    }

    suspend fun loginAdmin(request: LoginRequest): LoginResponse {
        val response = api.login(request)
        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Resposta vazia no servidor")
        } else {
            throw when (response.code()) {
                401 -> AuthException.InvalidCredentials()
                403 -> AuthException.BlockedUser()
                else -> AuthException.Unknown()
            }
        }
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): MessageResponse {
        val response = api.recoveryPassword(request)
        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Resposta vazia do servidor")
        } else {
            throw Exception(response.errorMessage())
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest): MessageResponse {
        val response = api.resetPassword(request)
        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Resposta vazia do servidor")
        } else {
            throw Exception(response.errorMessage())
        }
    }

}