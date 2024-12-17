package com.example.seedapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.seedapp.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var sharedPreferences: android.content.SharedPreferences
    private var isFirstRun = true
    private var tapped = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        initializeViews()
        setupBackgroundVideo()

        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Chiude la LoadingActivity
        }, 3000)
    }

    private fun initializeViews() {
        progressBar = binding.progressBar
    }

    private fun setupBackgroundVideo() {
        val backgroundVideoView = binding.videoBackground
        val videoPath = "android.resource://$packageName/${R.raw.caricamento}"
        backgroundVideoView.setVideoURI(Uri.parse(videoPath))
        backgroundVideoView.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f)
            backgroundVideoView.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Pulisce il ritardo se l'Activity viene distrutta
    }


}
