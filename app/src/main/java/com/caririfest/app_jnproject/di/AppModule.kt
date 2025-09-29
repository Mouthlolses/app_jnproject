package com.caririfest.app_jnproject.di

import android.content.Context
import com.caririfest.data.datasource.database.AppDatabase
import com.caririfest.data.datasource.repository.EventsRepository
import com.caririfest.network.data.EventsApi
import com.caririfest.network.data.EventsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): EventsApiService = EventsApi.retrofitService

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideRepository(api: EventsApiService, db: AppDatabase): EventsRepository =
        EventsRepository(api, db.eventDao())

}
