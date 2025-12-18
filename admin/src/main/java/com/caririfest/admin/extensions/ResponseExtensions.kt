package com.caririfest.admin.extensions

import com.caririfest.admin.model.recovery.MessageResponse
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.errorMessage(): String {
    val errorJson = errorBody()?.string()
    return try {
        Gson().fromJson(errorJson, MessageResponse::class.java).message
    } catch (e: Exception) {
        "Erro inesperado"
    }
}