package com.example.peptalkgenerator.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val REMINDER_ENABLED_STATE = booleanPreferencesKey("reminder_enabled_state")
        const val TAG = "UserPreferencesRepo"
    }

    val reminderEnabledState: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[REMINDER_ENABLED_STATE] ?: true
        }

    suspend fun saveReminderState(reminderEnabledState: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMINDER_ENABLED_STATE] = reminderEnabledState
        }
    }
}