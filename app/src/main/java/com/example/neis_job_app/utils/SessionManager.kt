package com.example.neis_job_app.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {
    companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val REMEMBER_ME_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("remember_me")
        val SAVED_EMAIL_KEY = stringPreferencesKey("saved_email")
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    val rememberMe: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: false
    }

    val savedEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SAVED_EMAIL_KEY]
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun saveRememberMe(enabled: Boolean, email: String? = null) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = enabled
            if (enabled && email != null) {
                preferences[SAVED_EMAIL_KEY] = email
            } else {
                preferences.remove(SAVED_EMAIL_KEY)
            }
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }
}
