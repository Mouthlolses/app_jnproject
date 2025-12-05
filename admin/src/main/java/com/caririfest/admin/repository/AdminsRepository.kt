package com.caririfest.admin.repository

import com.caririfest.admin.model.login.LoginRequest
import com.caririfest.admin.model.login.LoginResponse
import com.caririfest.admin.model.register.AdminCreateRequest
import com.caririfest.admin.model.register.AdminResponse
import com.caririfest.admin.network.AdminsApiService

class AdminsRepository(
    private val api: AdminsApiService
) {
    suspend fun createAdmin(request: AdminCreateRequest): AdminResponse {
        val response = api.createAdmin(request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Resposta vazia no servidor")
        } else {
            throw Exception("Erro HTTP: ${response.code()} - ${response.message()}")
        }
    }

    suspend fun loginAdmin(request: LoginRequest): LoginResponse {
        val response = api.login(request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Resposta vazia no servidor")
        } else {
            throw Exception("Error: ${response.code()} - ${response.message()}")
        }
    }
}