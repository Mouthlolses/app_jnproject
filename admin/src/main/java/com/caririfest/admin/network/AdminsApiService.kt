package com.caririfest.admin.network

import com.caririfest.admin.model.login.LoginRequest
import com.caririfest.admin.model.login.LoginResponse
import com.caririfest.admin.model.recovery.ForgotPasswordRequest
import com.caririfest.admin.model.recovery.MessageResponse
import com.caririfest.admin.model.recovery.ResetPasswordRequest
import com.caririfest.admin.model.register.AdminCreateRequest
import com.caririfest.admin.model.register.AdminResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdminsApiService {

    //register
    @POST("admins")
    suspend fun createAdmin(@Body request: AdminCreateRequest): Response<AdminResponse>

    //auth - login
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    //recover - password, send email
    @POST("auth/forgot-password")
    suspend fun recoveryPassword(@Body request: ForgotPasswordRequest): Response<MessageResponse>

    //recover - password, success or not
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<MessageResponse>
}