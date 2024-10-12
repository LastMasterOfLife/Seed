package com.example.seedapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seedapp.Data.SoundAdapter
import com.example.seedapp.databinding.ActivitySoundBinding

class SoundActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySoundBinding
    private lateinit var soundAdapter: SoundAdapter
    private val sounds = listOf("Suono 1", "Suono 2", "Suono 3", "Suono 4") // Lista di suoni di esempio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed();
        }

        // Carica preferenze per lo stato dello switch e il suono selezionato
        val preferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        binding.switchSound.isChecked = preferences.getBoolean("SOUND_ENABLED", true)

        // Gestisci il cambiamento dello switch
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean("SOUND_ENABLED", isChecked).apply()
        }

        // Imposta la RecyclerView con un adattatore e il binding
        soundAdapter = SoundAdapter(sounds) { selectedSound ->
            // Quando un suono è selezionato, salva nelle preferenze
            preferences.edit().putString("DEFAULT_SOUND", selectedSound).apply()
            Toast.makeText(this, "Suono predefinito: $selectedSound", Toast.LENGTH_SHORT).show()
        }
        binding.soundRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.soundRecyclerView.adapter = soundAdapter
    }
}