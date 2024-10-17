package com.example.seedapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seedapp.databinding.ActivityLanguageBinding
import java.util.Locale

class LanguageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.eng.setOnClickListener { showRestartDialog("en") }
        binding.italiano.setOnClickListener { showRestartDialog("it") }
        binding.back.setOnClickListener { onBackPressed() }
    }

    private fun showRestartDialog(languageCode: String) {
        AlertDialog.Builder(this)
            .setTitle("Cambio lingua")
            .setMessage("Per applicare la nuova lingua è necessario riavviare l'app. Vuoi continuare?")
            .setPositiveButton("Ok") { _, _ ->
                setLocale(languageCode)
                restartApp()
            }
            .setNegativeButton("Annulla") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Salva la lingua selezionata nelle SharedPreferences
        getSharedPreferences("app_preferences", Context.MODE_PRIVATE).edit()
            .putString("LANGUAGE_KEY", languageCode)
            .apply()
    }

    private fun restartApp() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
