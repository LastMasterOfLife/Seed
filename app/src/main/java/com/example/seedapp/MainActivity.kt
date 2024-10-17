package com.example.seedapp

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.seedapp.databinding.ActivityMainBinding
import com.example.seedapp.fragments.CommunityFragment
import com.example.seedapp.fragments.HomeFragment
import com.example.seedapp.fragments.SeedFragment
import com.example.seedapp.fragments.SettingsFragment
import com.example.seedapp.fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        applySavedLanguage()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Inizializza LanguageManager e carica la lingua salvata

        setContentView(binding.root)

        // Controlla se l'autenticazione biometrica è abilitata
        val sharedPreferences = getSharedPreferences("biometric_prefs", Context.MODE_PRIVATE)
        val biometricEnabled = sharedPreferences.getBoolean("biometric_enabled", false)

        if (biometricEnabled) {
            //showBiometricPrompt()
        } else {
            // Se l'autenticazione biometrica non è abilitata, apri direttamente la schermata principale
            setContentView(R.layout.activity_main)
        }


        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set default fragment
        supportFragmentManager.beginTransaction().replace(binding.container.id, SeedFragment()).commit()

        // default icon Navigationbar
        bottomNavigationView.selectedItemId = R.id.seed

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.game -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                    true
                }
                R.id.community -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, CommunityFragment()).commit()
                    true
                }
                R.id.seed -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SeedFragment()).commit()
                    true
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, UserFragment()).commit()
                    true
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }


    private fun applySavedLanguage() {
        val preferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        val languageCode = preferences.getString("LANGUAGE_KEY", "it") // Inglese di default

        val locale = Locale(languageCode!!)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}