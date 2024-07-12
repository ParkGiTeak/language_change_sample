package com.example.languagechange.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "language")

object PreferenceKeys {
    val savedLanguage = stringPreferencesKey("SavedLanguage")
}

suspend fun DataStore<Preferences>.updateSavedLanguage(language: String) {
    this.edit { preferences ->
        preferences[PreferenceKeys.savedLanguage] = language
    }
}

fun DataStore<Preferences>.getSavedLanguage(): Flow<String> {
    return this.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferenceKeys.savedLanguage] ?: LanguageChangeUtil.languageDefault
        }
        .distinctUntilChanged()
}