package com.example.languagechange.util

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

object LanguageChangeUtil {
    const val languageDefault: String = "DEFAULT"
    const val languageKo: String = "ko"
    const val languageEn: String = "en"

    val sysLanguage: String = Locale.getDefault().language
    var savedLanguage: String? = null

    fun updateBaseContextLocale(context: Context): Context = runBlocking {
        savedLanguage = context.datastore.getSavedLanguage().first()
        Log.d("LanguageLog", "저장된 언어: $savedLanguage")
        if(savedLanguage == languageDefault) {
            context
        } else {
            val locale = Locale(savedLanguage ?: sysLanguage)
            Locale.setDefault(locale)
            updateResourcesLocale(context, locale)
        }
    }

    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}