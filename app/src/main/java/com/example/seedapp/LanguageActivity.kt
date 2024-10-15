package com.example.seedapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
            showRestartDialog("en")

        }

        binging.italiano.setOnClickListener {
            showRestartDialog("it")
        }

        binging.back.setOnClickListener {
            onBackPressed();
        }

    }

    private fun showRestartDialog(languageCode: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cambio lingua")
        builder.setMessage("Per applicare la nuova lingua è necessario riavviare l'app. Vuoi continuare?")

        builder.setPositiveButton("Ok") { _, _ ->
            // Chiudi e riapri l'app
            setLocale(languageCode)
            restartApp()
        }

        builder.setNegativeButton("Annulla") { dialog, _ ->
            // Chiudi il popup senza fare nulla
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun restartApp() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
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