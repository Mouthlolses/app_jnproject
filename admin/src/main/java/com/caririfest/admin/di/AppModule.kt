package com.caririfest.admin.di

import com.caririfest.admin.network.AdminsApiService
import com.caririfest.admin.repository.AdminsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAdminsRepository(
        api: AdminsApiService
    ): AdminsRepository {
        return AdminsRepository(api)
    }
}