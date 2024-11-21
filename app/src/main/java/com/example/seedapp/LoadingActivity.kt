package com.example.seedapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ThemedSpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.seedapp.databinding.ActivityLoadingBinding

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding
    private lateinit var progressBar: ProgressBar
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar
        val layout = binding.layoutloading

        // Crea e posiziona le immagini sovrapposte
        val image1 = ImageView(this)
        image1.setImageResource(R.drawable.pianta_1)
        val image2 = ImageView(this)
        image2.setImageResource(R.drawable.pianta_2)
        val image3 = ImageView(this)
        image3.setImageResource(R.drawable.pianta_3)
        val image4 = ImageView(this)
        image4.setImageResource(R.drawable.pianta_4)
        val image5 = ImageView(this)
        image5.setImageResource(R.drawable.pianta_5)

        // Aggiungi le immagini al layout
        layout.addView(image1)
        layout.addView(image2)
        layout.addView(image3)
        layout.addView(image4)
        layout.addView(image5)

        // Centrare le immagini nel ConstraintLayout
        val params = arrayOf(
            image1, image2, image3, image4, image5
        ).map { image ->
            val p = image.layoutParams as ConstraintLayout.LayoutParams
            //p.topToTop = layout.id
            p.bottomToBottom = layout.id
            p.startToStart = layout.id
            p.endToEnd = layout.id
            p
        }

        image1.layoutParams = params[0]
        image2.layoutParams = params[1]
        image3.layoutParams = params[2]
        image4.layoutParams = params[3]
        image5.layoutParams = params[4]

        // Imposta la trasparenza iniziale delle immagini
        val images = arrayOf(image1, image2, image3, image4, image5)
        images.forEachIndexed { index, image ->
            image.alpha = if (index == 0) 1f else 0f
        }

        // Durata di fade-in e fade-out per ogni immagine
        val fadeDuration = 1700L // Durata di fade-in e fade-out

        // Crea gli ObjectAnimator per ogni immagine
        val animators = mutableListOf<ObjectAnimator>()

        // Animazione in avanti (dalla 1 alla 5)
        for (i in 0 until images.size - 1) {
            val fadeOut = ObjectAnimator.ofFloat(images[i], "alpha", 1f, 0f)
            fadeOut.duration = fadeDuration
            fadeOut.interpolator = AccelerateDecelerateInterpolator()

            val fadeIn = ObjectAnimator.ofFloat(images[i + 1], "alpha", 0f, 1f)
            fadeIn.duration = fadeDuration
            fadeIn.interpolator = AccelerateDecelerateInterpolator()

            fadeOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fadeIn.start()
                }
            })

            animators.add(fadeOut)
            animators.add(fadeIn)
        }

        // Animazione indietro (dalla 5 alla 1)
        for (i in images.size - 1 downTo 1) {
            val fadeOut = ObjectAnimator.ofFloat(images[i], "alpha", 1f, 0f)
            fadeOut.duration = fadeDuration
            fadeOut.interpolator = AccelerateDecelerateInterpolator()

            val fadeIn = ObjectAnimator.ofFloat(images[i - 1], "alpha", 0f, 1f)
            fadeIn.duration = fadeDuration
            fadeIn.interpolator = AccelerateDecelerateInterpolator()

            fadeOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    fadeIn.start()
                }
            })

            animators.add(fadeOut)
            animators.add(fadeIn)
        }

        // Usa un AnimatorSet per eseguire tutte le animazioni in sequenza
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(animators as List<Animator>?)
        animatorSet.start()

        // Avvia la progress bar
        simulateLoading()
    }

    private fun simulateLoading() {
        // Simula un caricamento con un Handler
        Thread {
            for (i in 1..100) {
                Thread.sleep(25)
                Thread.sleep(55)
                Thread.sleep(80)
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
