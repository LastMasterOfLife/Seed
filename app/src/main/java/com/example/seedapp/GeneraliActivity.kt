package com.example.seedapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityGeneraliBinding
import com.example.seedapp.databinding.ActivityPrivacyBinding

class GeneraliActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneraliBinding
    private lateinit var mediaPlayer: MediaPlayer

    private val recreateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            recreate() // Ricrea l'Activity quando ricevi il broadcast
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneraliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchsuono.isChecked
        binding.linearSuoneria.visibility = View.VISIBLE
        binding.playWistley.visibility = View.VISIBLE
    }
    private fun vibrateWithIntensity(vibrator: Vibrator, intensity: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(
                500L, // Durata della vibrazione in millisecondi
                intensity // Intensità della vibrazione da 1 a 255
            )
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(500L) // Compatibilità per vecchie versioni, intensità non supportata
        }
    }

    private fun savePreferences(isVibrationEnabled: Boolean, intensity: Int) {
        val sharedPref = getSharedPreferences("VibrationSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("vibrationEnabled", isVibrationEnabled)
            putInt("vibrationIntensity", intensity)
            apply() // Salva le preferenze
        }

        val sharedPref1 = getSharedPreferences("NotificationSettings", Context.MODE_PRIVATE)
        with(sharedPref1.edit()) {
            putBoolean("NotificationEnabled", isVibrationEnabled)
            apply() // Salva le preferenze
        }
    }

    // Metodo per salvare la preferenza dello stato del suono
    private fun saveSoundPreference(isSoundEnabled: Boolean) {
        val sharedPref = getSharedPreferences("SoundSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("soundEnabled", isSoundEnabled)
            apply() // Salva la preferenza
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Rilascia il MediaPlayer quando l'activity viene distrutta
        mediaPlayer.release()
        //unregisterReceiver(recreateReceiver) // Rimuovi il BroadcastReceiver
    }

    override fun onResume() {
        super.onResume()

        // Recupera lo stato dello switch dal SharedPreferences
        val sharedPrefS = getSharedPreferences("SoundSettings", Context.MODE_PRIVATE)
        val isSoundEnabled = sharedPrefS.getBoolean("soundEnabled", true)
        binding.switchsuono.isChecked = isSoundEnabled // Imposta lo stato iniziale

        // Registra il BroadcastReceiver
        //registerReceiver(recreateReceiver, IntentFilter("com.example.seedapp.RECREATE_ALL_ACTIVITIES"))

        // Recupera il testo del button selezionato dalle SharedPreferences
        val sharedPreferencesS = getSharedPreferences("SuoneriePrefs", Context.MODE_PRIVATE)
        val selectedButtonText = sharedPreferencesS.getString("selected_button_text", "whistle")

        // Inizializza il MediaPlayer con il file audio
        mediaPlayer = MediaPlayer.create(this, R.raw.whistle_notification_sound)

        val sharedPref = getSharedPreferences("VibrationSettings", Context.MODE_PRIVATE)
        val isVibrationEnabled = sharedPref.getBoolean("vibrationEnabled", true)
        val intensity = sharedPref.getInt("vibrationIntensity", 50)

        val sharedPreferences = getSharedPreferences("NotificationSettings", Context.MODE_PRIVATE)
        val isattivo = sharedPref.getBoolean("NotificationEnabled", true)



        // Imposta il testo del button con quello selezionato
        binding.playWistley.text = selectedButtonText


        // Imposta lo stato iniziale di Switch e SeekBar
        binding.switchVivration.isChecked = isVibrationEnabled
        binding.intensitySeekBar.progress = intensity
        binding.intensitySeekBar.visibility = if (isVibrationEnabled) View.VISIBLE else View.GONE




        binding.switchobbiettivi.alpha = 0.5f
        binding.switchsostenibilit.alpha = 0.5f
        binding.switchmeteo.alpha = 0.5f

        binding.switchobbiettivi.isActivated = false
        binding.switchmeteo.isActivated = false
        binding.switchsostenibilit.isActivated = false

        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        // Esegue la vibrazione con l'intensità salvata (se attivo)
        if (isVibrationEnabled) {
            vibrateWithIntensity(vibrator,intensity)
        }

        binding.switchnotification.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked){
                binding.switchobbiettivi.alpha = 1.0f
                binding.switchsostenibilit.alpha = 1.0f
                binding.switchmeteo.alpha = 1.0f

                binding.switchobbiettivi.isChecked = isChecked
                binding.switchmeteo.isChecked = isChecked
                binding.switchsostenibilit.isChecked = isChecked

                binding.switchobbiettivi.visibility = View.VISIBLE
                binding.switchsostenibilit.visibility = View.VISIBLE
                binding.switchmeteo.visibility = View.VISIBLE
            }
            else{
                binding.switchobbiettivi.alpha = 0.5f
                binding.switchsostenibilit.alpha = 0.5f
                binding.switchmeteo.alpha = 0.5f

                binding.switchobbiettivi.isChecked = isChecked
                binding.switchmeteo.isChecked = isChecked
                binding.switchsostenibilit.isChecked = isChecked

                binding.switchobbiettivi.visibility = View.GONE
                binding.switchsostenibilit.visibility = View.GONE
                binding.switchmeteo.visibility = View.GONE
            }
        }


        binding.switchsuono.setOnCheckedChangeListener { _, isChecked ->

            saveSoundPreference(isChecked)
            binding.linearSuoneria.visibility = if (isChecked) View.VISIBLE else View.GONE
            binding.playWistley.visibility = if (isChecked) View.VISIBLE else View.GONE
        }


        binding.switchVivration.setOnCheckedChangeListener { _, isChecked ->
            // Mostra o nasconde la SeekBar in base allo stato dello switch
            binding.intensitySeekBar.visibility = if (isChecked) View.VISIBLE else View.GONE

            // Esegue la vibrazione con l'intensità selezionata
            if (isChecked) {
                val intensity = binding.intensitySeekBar.progress
                vibrateWithIntensity(vibrator, intensity)
            }
        }

        binding.intensitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Aggiorna la vibrazione ogni volta che l'intensità cambia
                if (binding.switchVivration.isChecked) {
                    vibrateWithIntensity(vibrator, progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.playWistley.setOnClickListener {

            val intent = Intent(this, SuonerieActivity::class.java)
            startActivity(intent)
        }

        binding.back.setOnClickListener {
            onBackPressed();
        }



    }
}