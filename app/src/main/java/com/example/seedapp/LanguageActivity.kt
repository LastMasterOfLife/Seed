package com.example.seedapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.Data.Settings
import com.example.seedapp.databinding.ActivityLanguageBinding
import com.example.seedapp.Data.db
import java.util.Locale

class LanguageActivity : AppCompatActivity() {


    private lateinit var binging : ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binging = ActivityLanguageBinding.inflate(layoutInflater)

        setContentView(binging.root)

        binging.eng.setOnClickListener {
            setLocale("en")

        }

        binging.italiano.setOnClickListener {
            setLocale("it")
        }

        binging.back.setOnClickListener {
            onBackPressed();
        }

    }


    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Salva la lingua selezionata nelle Shared Preferences
        val preferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        preferences.edit().putString("LANGUAGE_KEY", languageCode).apply()

        // Ricarica l'attività per applicare il cambiamento
        recreate()
    }

}