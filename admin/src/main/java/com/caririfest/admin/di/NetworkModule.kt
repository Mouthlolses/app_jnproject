package com.caririfest.admin.di

import com.caririfest.admin.network.AdminsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val ADMIN_URL = "http://10.0.2.2:8080/" //Emulador

    //private const val ADMIN_URL = "http://192.168.0.7:8080/"


    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ADMIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    @Provides
    @Singleton
    fun provideAdminsApiService(retrofit: Retrofit): AdminsApiService =
        retrofit.create(AdminsApiService::class.java)

}