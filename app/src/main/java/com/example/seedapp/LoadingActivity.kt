package com.example.seedapp

import android.content.Intent
import com.example.seedapp.databinding.ActivityLoadingBinding
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    private lateinit var progressBar: ProgressBar
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar


        // Avvia la progress bar
        simulateLoading()
    }

    private fun simulateLoading() {
        // Simula un caricamento con un Handler
        Thread {
            for (i in 1..100) {
                Thread.sleep(50) // Simula il tempo di caricamento
                handler.post {
                    progressBar.progress = i
                }
            }
            // Una volta completato il caricamento, avvia l'activity desiderata
            startActivity(Intent(this, MainActivity::class.java)) // Sostituisci con l'activity successiva
            finish() // Chiudi l'activity di caricamento
        }.start()
    }
}