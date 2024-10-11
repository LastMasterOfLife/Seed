package com.example.seedapp.Data

import android.app.Activity
import android.content.Context
import java.util.Locale

class db {
    val languages = arrayOf("en", "it", "fr") // Inglese, Italiano, Francese

    fun setLocale(languageCode: String, context: Context) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)

        // Riavvia l'Activity per applicare il cambio di lingua
        if (context is Activity) {
            context.recreate()
        }
    }
}