package com.caririfest.admin.network

import com.caririfest.admin.model.AdminCreateRequest
import com.caririfest.admin.model.AdminResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminsApiService {

    @POST("admins")
    suspend fun createAdmin(@Body request: AdminCreateRequest): Response<AdminResponse>

}