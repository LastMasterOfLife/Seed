package com.example.seedapp

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CALENDAR
import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_CALENDAR
import android.Manifest.permission.WRITE_CONTACTS
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

    // Nome della chiave per il primo accesso nelle SharedPreferences
    private val FIRST_ACCESS_KEY = "first_access_key"
    private val PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        applySavedLanguage()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Inizializza LanguageManager e carica la lingua salvata

        setContentView(binding.root)

        // Controlla se è il primo accesso
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstAccess = sharedPreferences.getBoolean(FIRST_ACCESS_KEY, true)

        if (isFirstAccess) {
            // Se è il primo accesso, richiede le autorizzazioni
            checkPermissions()
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

    private fun checkPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        // Controllo delle autorizzazioni necessarie
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(ACCESS_FINE_LOCATION)
        }

        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(WRITE_EXTERNAL_STORAGE)
            permissionsNeeded.add(READ_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(this, READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(READ_CALENDAR)
            permissionsNeeded.add(WRITE_CALENDAR)
        }

        if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(RECORD_AUDIO)
        }

        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(READ_CONTACTS)
            permissionsNeeded.add(WRITE_CONTACTS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(POST_NOTIFICATIONS)
            }
        }

        // Richiesta delle autorizzazioni mancanti
        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Se tutte le autorizzazioni sono già concesse, salva che l'utente ha effettuato il primo accesso
            saveFirstAccess()
        }
    }



    // Gestione del risultato delle autorizzazioni
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                // Verifica quali autorizzazioni sono state concesse
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        // Logica per autorizzazione negata
                        // Mostra messaggio o gestisci il caso in cui l'utente nega i permessi
                    }
                }
                // Se tutte le autorizzazioni sono concesse, salva il primo accesso
                saveFirstAccess()
            }
        }
    }

    private fun saveFirstAccess() {
        // Salva nelle SharedPreferences che l'utente ha effettuato il primo accesso
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(FIRST_ACCESS_KEY, false)  // Indica che non è più il primo accesso
        editor.apply()
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