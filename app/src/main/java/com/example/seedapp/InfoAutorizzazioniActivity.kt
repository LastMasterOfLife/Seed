package com.example.seedapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityInfoAutorizzazioniBinding

class InfoAutorizzazioniActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoAutorizzazioniBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val stringaRicevuta = intent.getStringExtra("autorizzazione")

        super.onCreate(savedInstanceState)
        binding = ActivityInfoAutorizzazioniBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrayDiAutorizzazioni = arrayOf("Internet", "Posizione", "Memoria", "Calendario","Notifiche","Audio","Contatti", "Archiviazione Sicura")

        binding.titoloautoriz.text = stringaRicevuta
        binding.swichautoriz.text = stringaRicevuta


        when (stringaRicevuta) {
            arrayDiAutorizzazioni[0] -> {
                // Caso 1
                binding.descrizautori.text = getString(R.string.descrizioneInternet)
            }
            arrayDiAutorizzazioni[1] -> {
                // Caso 2
                binding.descrizautori.text = getString(R.string.descrizionePosizione)
            }
            arrayDiAutorizzazioni[2] -> {
                // Caso 3
                binding.descrizautori.text = getString(R.string.descrizionememoria)
            }
            arrayDiAutorizzazioni[3] -> {
                // Caso 3
                binding.descrizautori.text = getString(R.string.descrizionecalendario)
            }
            arrayDiAutorizzazioni[4] -> {
                // Caso 3
                binding.descrizautori.text = getString(R.string.descrizionenotifice)
                binding.swichautoriz.isChecked
            }
            arrayDiAutorizzazioni[5] -> {
            // Caso 3
                binding.descrizautori.text = getString(R.string.descrizioneaudio)
                binding.swichautoriz.isChecked
            }
            arrayDiAutorizzazioni[6] -> {
                // Caso 3
                binding.descrizautori.text = getString(R.string.descrizionecontatti)
            }
            else -> {
                // Caso predefinito
                binding.descrizautori.text = getString(R.string.descrizionearchiviazionesicura)
                binding.swichautoriz.isChecked
            }
        }


        binding.back.setOnClickListener {
            onBackPressed()
        }

    }
}