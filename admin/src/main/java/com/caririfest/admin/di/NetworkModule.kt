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

    private const val ADMIN_URL = "https://caririfestserver-production-b05f.up.railway.app/"

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