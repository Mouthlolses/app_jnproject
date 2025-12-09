package com.caririfest.admin.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.authDataStore by preferencesDataStore(name = "auth_preferences")

class AuthPreferences(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val ADMIN_NAME = stringPreferencesKey("admin_name")
        private val ADMIN_EMAIL = stringPreferencesKey("admin_email")

    }

    val isLoggedIn: Flow<Boolean> = context.authDataStore.data
        .map { preferences -> preferences[IS_LOGGED_IN] ?: false }

    val adminName: Flow<String?> = context.authDataStore.data
        .map { preferences -> preferences[ADMIN_NAME] }

    val adminEmail: Flow<String?> = context.authDataStore.data
        .map { preferences -> preferences[ADMIN_EMAIL] }


    suspend fun setLoggedIn(value: Boolean) {
        context.authDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }

    suspend fun saveAdminName(name: String) {
        context.authDataStore.edit { preferences ->
            preferences[ADMIN_NAME] = name
        }
    }

    suspend fun saveAdminEmail(email: String) {
        context.authDataStore.edit { preferences ->
            preferences[ADMIN_EMAIL] = email
        }
    }

    suspend fun clearSession() {
        context.authDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
            preferences.remove(ADMIN_NAME)
            preferences.remove(ADMIN_EMAIL)
        }
    }
}