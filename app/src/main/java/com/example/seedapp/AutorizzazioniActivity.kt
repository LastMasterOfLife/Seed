package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityAutorizzazioniBinding

class AutorizzazioniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAutorizzazioniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutorizzazioniBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.internet.setOnClickListener {

            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.internet))
            startActivity(intent)
        }

        binding.posizione.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.posizione))
            startActivity(intent)
        }

        binding.memoria.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.memoriainterna))
            startActivity(intent)
        }

        binding.calendario.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.calendario))
            startActivity(intent)
        }

        binding.notifiche.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.notifiche))
            startActivity(intent)
        }

        binding.audio.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.audio))
            startActivity(intent)
        }

        binding.contatti.setOnClickListener {val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.contatti))
            startActivity(intent)
        }

        binding.archiviazionesic.setOnClickListener {
            val intent = Intent(this, InfoAutorizzazioniActivity::class.java)
            intent.putExtra("autorizzazione", getString(R.string.archiviazionesicura))
            startActivity(intent)
        }
    }
}