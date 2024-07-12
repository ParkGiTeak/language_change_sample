package com.example.languagechange.application

import android.app.Application
import com.example.languagechange.util.LanguageChangeUtil

class LanguageChangeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LanguageChangeUtil.updateBaseContextLocale(this@LanguageChangeApplication)
    }
}