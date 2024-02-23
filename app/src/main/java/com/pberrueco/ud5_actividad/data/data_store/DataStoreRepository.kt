package com.pberrueco.ud5_actividad.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.pberrueco.ud5_actividad")

object DataStoreRepository {
    suspend fun saveUser(context: Context, userName: String, password: String) {
        val userNameKey = stringPreferencesKey("user_name")
        val passwordKey = stringPreferencesKey("password")

        context.dataStore.edit { editor ->
            editor[userNameKey] = userName
            editor[passwordKey] = password
        }
    }

    suspend fun getUser(context: Context): Flow<String> {
        val userNameKey = stringPreferencesKey("user_name")
        return context.dataStore.data.map { editor ->
            editor[userNameKey] ?: "User nulo"
        }
    }

    suspend fun getPassword(context: Context): Flow<String> {
        val passwordKey = stringPreferencesKey("password")
        return context.dataStore.data.map { editor ->
            editor[passwordKey] ?: "Contraseña nula"
        }
    }

    suspend fun deleteUser(context: Context, username: String) {
        val userNameKey = stringPreferencesKey("user_name")
        val passwordKey = stringPreferencesKey("password")

        context.dataStore.edit { editor ->
            editor.remove(userNameKey)
            editor.remove(passwordKey)
        }
    }
}