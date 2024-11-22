package com.example.seedapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Intent
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
    private val handler = Handler()
    private lateinit var textView: TextView
    private var tapped = false

    private lateinit var sharedPreferences: android.content.SharedPreferences
    private var isFirstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        initializeViews()
        setupTextViewAnimation()
        setupClickListener()
        setupImagesAndAnimations()
        simulateLoading()

        /*
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstRun) {
                val editor = sharedPreferences.edit()
                editor.putBoolean("isFirstRun", false)
                editor.apply()

                // Vai alla pagina di creazione account
                val intent = Intent(this, Create_accountActivity::class.java)
                startActivity(intent)
            } else {
                // Vai al MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            finish() // Chiude la SplashActivity
        }, 2000) // Imposta il ritardo della splash page (ad esempio, 2 secondi)

         */
    }


    private fun initializeViews() {
        progressBar = binding.progressBar
        textView = binding.tocca
    }

    private fun setupTextViewAnimation() {
        handler.postDelayed({
            startTextAnimation(textView)
            tapped = true
        }, 6000)
    }

    private fun setupClickListener() {
        val superfice = binding.schermata
        superfice.setOnClickListener {
            if (tapped) {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isFirstRun) {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isFirstRun", false)
                        editor.apply()

                        // Vai alla pagina di creazione account
                        val intent = Intent(this, Create_accountActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Vai al MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                    finish() // Chiude la SplashActivity
                }, 0) // Imposta il ritardo della splash page (ad esempio, 2 secondi)
            }
        }
    }

    private fun setupImagesAndAnimations() {
        val layout = binding.layoutloading

        // Crea e aggiungi le immagini
        val images = createImages()
        images.forEach { layout.addView(it) }

        // Centra le immagini nel layout
        centerImagesInLayout(images, layout)

        // Imposta la trasparenza iniziale
        setInitialImageAlpha(images)

        // Esegui le animazioni per le immagini
        startImageAnimations(images)
    }

    private fun createImages(): Array<ImageView> {
        val imageResources = arrayOf(
            R.drawable.pianta_1, R.drawable.pianta_2, R.drawable.pianta_3,
            R.drawable.pianta_4, R.drawable.pianta_5
        )

        return imageResources.map { res ->
            ImageView(this).apply {
                setImageResource(res)
            }
        }.toTypedArray()
    }

    private fun centerImagesInLayout(images: Array<ImageView>, layout: ConstraintLayout) {
        val params = images.map {
            val p = it.layoutParams as ConstraintLayout.LayoutParams
            p.bottomToBottom = layout.id
            p.startToStart = layout.id
            p.endToEnd = layout.id
            p
        }

        images.zip(params).forEach { (image, param) ->
            image.layoutParams = param
        }
    }

    private fun setInitialImageAlpha(images: Array<ImageView>) {
        images.forEachIndexed { index, image ->
            image.alpha = if (index == 0) 1f else 0f
        }
    }

    private fun startImageAnimations(images: Array<ImageView>) {
        val fadeDuration = 1700L
        val animators = mutableListOf<Animator>()

        // Animazione in avanti
        for (i in 0 until images.size - 1) {
            createImageFadeAnimation(images[i], images[i + 1], fadeDuration, animators)
        }

        // Animazione indietro
        for (i in images.size - 1 downTo 1) {
            createImageFadeAnimation(images[i], images[i - 1], fadeDuration, animators)
        }

        // Esegui le animazioni in sequenza
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(animators)
        animatorSet.start()
    }

    private fun createImageFadeAnimation(fromImage: ImageView, toImage: ImageView, duration: Long, animators: MutableList<Animator>) {
        val fadeOut = ObjectAnimator.ofFloat(fromImage, "alpha", 1f, 0f).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
        }

        val fadeIn = ObjectAnimator.ofFloat(toImage, "alpha", 0f, 1f).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
        }

        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                fadeIn.start()
            }
        })

        animators.add(fadeOut)
        animators.add(fadeIn)
    }

    private fun startTextAnimation(textView: TextView) {
        val fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f).apply {
            duration = 1000L
        }

        val fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f).apply {
            duration = 1000L
            startDelay = 1000L
        }

        val animatorSet = AnimatorSet().apply {
            playSequentially(fadeIn, fadeOut)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start()
                }
            })
        }
        animatorSet.start()
    }

    private fun simulateLoading() {
        Thread {
            for (i in 1..100) {
                Thread.sleep(25)
                Thread.sleep(55)
                Thread.sleep(80)
                handler.post {
                    progressBar.progress = i
                }
            }
            if (!tapped) {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isFirstRun) {
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isFirstRun", false)
                        editor.apply()

                        // Vai alla pagina di creazione account
                        val intent = Intent(this, Create_accountActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Vai al MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                    finish() // Chiude la SplashActivity
                }, 0) // Imposta il ritardo della splash page (ad esempio, 2 secondi)
            }
        }.start()
    }
}
