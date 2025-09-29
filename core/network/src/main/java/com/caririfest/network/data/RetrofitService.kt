package com.caririfest.network.data

import com.caririfest.network.model.EventResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://firestore.googleapis.com/v1/"


private val json = Json {
    ignoreUnknownKeys = true

}
private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface EventsApiService {
    @GET("projects/eventapp-d6adb/databases/(default)/documents/event")
    suspend fun getEvents(): Response<EventResponse>

}

object EventsApi {
    val retrofitService: EventsApiService by lazy {
        retrofit.create(EventsApiService::class.java)
    }
}
