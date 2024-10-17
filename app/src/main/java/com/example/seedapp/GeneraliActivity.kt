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
import androidx.appcompat.app.AppCompatActivity
import com.example.seedapp.databinding.ActivityGeneraliBinding

class GeneraliActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneraliBinding
    private lateinit var mediaPlayer: MediaPlayer

    private val recreateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneraliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInitialUI()
        registerEventListeners()
    }

    private fun setupInitialUI() {
        binding.switchsuono.isChecked
        binding.linearSuoneria.visibility = View.VISIBLE
        binding.playWistley.visibility = View.VISIBLE
    }

    private fun registerEventListeners() {
        binding.switchsuono.setOnCheckedChangeListener { _, isChecked ->
            handleSoundSwitch(isChecked)
        }

        binding.switchVivration.setOnCheckedChangeListener { _, isChecked ->
            handleVibrationSwitch(isChecked)
        }

        binding.switchnotification.setOnCheckedChangeListener { _, isChecked ->
            handleNotificationSwitch(isChecked)
        }

        binding.intensitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateVibration(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.playWistley.setOnClickListener {
            startActivity(Intent(this, SuonerieActivity::class.java))
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun handleSoundSwitch(isChecked: Boolean) {
        saveSoundPreference(isChecked)
        binding.linearSuoneria.visibility = if (isChecked) View.VISIBLE else View.GONE
        binding.playWistley.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    private fun handleVibrationSwitch(isChecked: Boolean) {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        binding.intensitySeekBar.visibility = if (isChecked) View.VISIBLE else View.GONE

        if (isChecked) {
            val intensity = binding.intensitySeekBar.progress
            vibrateWithIntensity(vibrator, intensity)
        }
    }

    private fun handleNotificationSwitch(isChecked: Boolean) {
        val switches = listOf(binding.switchobbiettivi, binding.switchsostenibilit, binding.switchmeteo)
        switches.forEach { switch ->
            switch.alpha = if (isChecked) 1.0f else 0.5f
            switch.isChecked = isChecked
            switch.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun updateVibration(intensity: Int) {
        if (binding.switchVivration.isChecked) {
            val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibrateWithIntensity(vibrator, intensity)
        }
    }

    private fun vibrateWithIntensity(vibrator: Vibrator, intensity: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(500L, intensity)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(500L)
        }
    }

    private fun savePreferences(isVibrationEnabled: Boolean, intensity: Int) {
        val sharedPref = getSharedPreferences("VibrationSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("vibrationEnabled", isVibrationEnabled)
            putInt("vibrationIntensity", intensity)
            apply()
        }
    }

    private fun saveSoundPreference(isSoundEnabled: Boolean) {
        val sharedPref = getSharedPreferences("SoundSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("soundEnabled", isSoundEnabled)
            apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onResume() {
        super.onResume()
        restoreUIState()
    }

    private fun restoreUIState() {
        val sharedPrefS = getSharedPreferences("SoundSettings", Context.MODE_PRIVATE)
        val isSoundEnabled = sharedPrefS.getBoolean("soundEnabled", true)
        binding.switchsuono.isChecked = isSoundEnabled

        val sharedPreferencesS = getSharedPreferences("SuoneriePrefs", Context.MODE_PRIVATE)
        binding.playWistley.text = sharedPreferencesS.getString("selected_button_text", "whistle")

        mediaPlayer = MediaPlayer.create(this, R.raw.whistle_notification_sound)

        val sharedPref = getSharedPreferences("VibrationSettings", Context.MODE_PRIVATE)
        binding.switchVivration.isChecked = sharedPref.getBoolean("vibrationEnabled", true)
        binding.intensitySeekBar.progress = sharedPref.getInt("vibrationIntensity", 50)
    }
}
