package com.example.seedapp

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



    }
}