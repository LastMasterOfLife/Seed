package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivitySicurezzaBinding

class SicurezzaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySicurezzaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySicurezzaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pass.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        binding.autoriz.setOnClickListener {
            val intent = Intent(this, AutorizzazioniActivity::class.java)
            startActivity(intent)
        }

        binding.elimdati.setOnClickListener {

        }

        binding.back.setOnClickListener {
            onBackPressed()
        }


    }
}