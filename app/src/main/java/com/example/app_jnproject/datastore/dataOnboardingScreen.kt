package com.example.app_jnproject.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKey {
    val ONBOARDING_SHOWN = booleanPreferencesKey("onboarding_show")
}