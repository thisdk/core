package io.github.thisdk.core.ds

import android.content.Context
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AppDataStore(private val context: Context) {

    suspend fun putString(key: String, value: String) {
        context.dataStore.edit { setting ->
            setting[stringPreferencesKey(key)] = value
        }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        context.dataStore.edit { setting ->
            setting[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun putInt(key: String, value: Int) {
        context.dataStore.edit { setting ->
            setting[intPreferencesKey(key)] = value
        }
    }

    suspend fun putLong(key: String, value: Long) {
        context.dataStore.edit { setting ->
            setting[longPreferencesKey(key)] = value
        }
    }

    suspend fun putFloat(key: String, value: Float) {
        context.dataStore.edit { setting ->
            setting[floatPreferencesKey(key)] = value
        }
    }

    suspend fun putDouble(key: String, value: Double) {
        context.dataStore.edit { setting ->
            setting[doublePreferencesKey(key)] = value
        }
    }

    suspend fun putStringSet(key: String, value: Set<String>) {
        context.dataStore.edit { setting ->
            setting[stringSetPreferencesKey(key)] = value
        }
    }

    fun getString(key: String): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    fun getBoolean(key: String): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }

    fun getInt(key: String): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: 0
        }
    }

    fun getLong(key: String): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: 0L
        }
    }

    fun getFloat(key: String): Flow<Float> {
        return context.dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: 0f
        }
    }

    fun getDouble(key: String): Flow<Double> {
        return context.dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: 0.0
        }
    }

    fun getStringSet(key: String): Flow<Set<String>> {
        return context.dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: setOf()
        }
    }

    fun getString4Sync(key: String): String? {
        val preferences = runBlocking { context.dataStore.data.first() }
        return preferences[stringPreferencesKey(key)]
    }

    fun getStringSet4Sync(key: String): Set<String> {
        val preferences = runBlocking { context.dataStore.data.first() }
        return preferences[stringSetPreferencesKey(key)] ?: setOf()
    }

}