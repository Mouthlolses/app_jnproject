package com.caririfest.admin.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.authDataStore by preferencesDataStore(name = "auth_preferences")

class AuthPreferences(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> = context.authDataStore.data
        .map { preferences -> preferences[IS_LOGGED_IN] ?: false }

    suspend fun setLoggedIn(value: Boolean) {
        context.authDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }
}