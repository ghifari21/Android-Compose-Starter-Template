package com.project.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExampleDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val EXAMPLE_KEY = stringPreferencesKey("example_key")
    }

    fun getExampleValue(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EXAMPLE_KEY]
        }
    }

    suspend fun saveExampleValue(value: String) {
        dataStore.edit { preferences ->
            preferences[EXAMPLE_KEY] = value
        }
    }
}
