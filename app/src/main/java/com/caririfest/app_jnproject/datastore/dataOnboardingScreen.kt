package com.caririfest.app_jnproject.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKey {
    val ONBOARDING_SHOWN = booleanPreferencesKey("onboarding_show")
}