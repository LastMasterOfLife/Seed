package com.example.seedapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityEliminaDatiBinding

class EliminaDatiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEliminaDatiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEliminaDatiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.descrizione.text = getString(R.string.descrizioneeliminadati)

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}