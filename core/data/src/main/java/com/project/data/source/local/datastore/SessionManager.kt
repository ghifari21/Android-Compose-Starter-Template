package com.project.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("jwt_token")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    /**
     * Get the current JWT token as a Flow.
     */
    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }
    }

    /**
     * Check if the user is currently logged in.
     */
    fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_IS_LOGGED_IN] ?: false
        }
    }

    /**
     * Save the JWT token and set the login status to true.
     */
    suspend fun saveSession(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
            preferences[KEY_IS_LOGGED_IN] = true
        }
    }

    /**
     * Clear the session (e.g. upon logout).
     */
    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_TOKEN)
            preferences[KEY_IS_LOGGED_IN] = false
        }
    }
}
