package com.example.seedapp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seedapp.Data.SuoneeriaAdapter
import com.example.seedapp.databinding.ActivitySuonerieBinding

class SuonerieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuonerieBinding
    private lateinit var adapter: SuoneeriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuonerieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val soundResources = listOf(R.raw.whistle_notification_sound, R.raw.bubble_notification, R.raw.facebook_notification_sound, R.raw.modern_soft_notification_sound)

        // Lista di testi per i pulsanti
        val buttonTexts = listOf("Whistle", "Bubble", "Facebook", "modern soft")

        // Imposta RecyclerView con Adapter
        adapter = SuoneeriaAdapter(this, buttonTexts, soundResources)
        binding.recyclerViewSuonerie.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSuonerie.adapter = adapter

        binding.back.setOnClickListener {
            onBackPressed();
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseMediaPlayer()  // Libera il MediaPlayer quando l’Activity viene distrutta
    }
}