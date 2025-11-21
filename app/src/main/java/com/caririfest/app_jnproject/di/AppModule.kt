package com.caririfest.app_jnproject.di

import android.content.Context
import com.caririfest.app_jnproject.R
import com.caririfest.data.datasource.dao.EventDao
import com.caririfest.data.datasource.dao.RecentEventDao
import com.caririfest.data.datasource.database.AppDatabase
import com.caririfest.data.datasource.repository.EventsRepository
import com.caririfest.data.datasource.repository.RecentEventsRepository
import com.caririfest.network.data.EventsApi
import com.caririfest.network.data.EventsApiService
import com.mapbox.common.MapboxOptions.accessToken
import com.mapbox.search.ApiType
import com.mapbox.search.SearchEngine
import com.mapbox.search.SearchEngineSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Prover o token
    @Provides
    @Singleton
    @Named("mapBoxToken")
    fun provideMapboxToken(@ApplicationContext context: Context): String {
        return context.getString(R.string.mapbox_access_token)
    }

    //PASSO 2: Prover o SearchEngine
    @Provides
    @Singleton
    fun provideSearchEngine(
        @Named("mapBoxToken") token: String,
    ): SearchEngine {

        val settings = SearchEngineSettings().apply {
            accessToken = token
        }

        return SearchEngine.createSearchEngine(
            apiType = ApiType.GEOCODING,
            settings = settings
        )
    }

    @Provides
    @Singleton
    fun provideApi(): EventsApiService = EventsApi.retrofitService

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    fun provideEventDao(db: AppDatabase): EventDao = db.eventDao()

    @Provides
    fun provideRecentEventDao(db: AppDatabase): RecentEventDao = db.recentEventDao()


    @Provides
    @Singleton
    fun provideEventsRepository(
        api: EventsApiService,
        eventDao: EventDao
    ): EventsRepository = EventsRepository(api, eventDao)

    @Provides
    @Singleton
    fun provideRecentEventsRepository(
        eventDao: EventDao,
        recentEventDao: RecentEventDao
    ): RecentEventsRepository = RecentEventsRepository(eventDao, recentEventDao)

}
