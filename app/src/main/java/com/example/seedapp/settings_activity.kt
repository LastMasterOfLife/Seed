package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivitySettingsBinding

class settings_activity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // Listener per il bottone "Generali"
        binding.generali.setOnClickListener {
            val intent = Intent(this, GeneraliActivity::class.java)
            startActivity(intent)
        }

        // Listener per il bottone "Lingua"
        binding.lingua.setOnClickListener {
            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)
        }

        // Listener per il bottone "Privacy"
        binding.privacy.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)
            startActivity(intent)
        }

        // Listener per il bottone "Policy"
        binding.policy.setOnClickListener {
            val intent = Intent(this, PolicyActivity::class.java)
            startActivity(intent)
        }

        // Listener per il bottone "Sicurezza"
        binding.sicurezza.setOnClickListener {
            val intent = Intent(this, SicurezzaActivity::class.java)
            startActivity(intent)
        }

        // Listener per il bottone "Community"
        binding.community.setOnClickListener {
            // Azione da definire
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}