package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN)



        binding.back.setOnClickListener {
            onBackPressed()
        }

        // Listener per il pulsante elimina
        binding.delete.setOnClickListener {
            clearApplicationData()
        }
    }
    fun clearApplicationData() {
        // Cancella tutti i database
        applicationContext.deleteDatabase("app_database")


        // Cancella file interni dell'app
        val filesDir = filesDir
        filesDir.deleteRecursively()


        // Cancella contenuti di cache
        val cacheDir = cacheDir
        cacheDir.deleteRecursively()


        // Mostra un messaggio di conferma
        Toast.makeText(this, "Tutti i dati sono stati eliminati correttamente", Toast.LENGTH_SHORT).show()


        // Optional: Chiudi l'attività o reindirizza
        val intent = Intent(this, Create_accountActivity::class.java)
        startActivity(intent)
        finish()
    }
}