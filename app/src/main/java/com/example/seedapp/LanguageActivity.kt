package com.example.seedapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.Data.Settings
import com.example.seedapp.databinding.ActivityLanguageBinding
import com.example.seedapp.Data.db
import java.util.Locale

class LanguageActivity : AppCompatActivity() {


    private lateinit var binging : ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binging = ActivityLanguageBinding.inflate(layoutInflater)

        setContentView(binging.root)

        binging.eng.setOnClickListener {
           // setLocale("en", this)
            Settings.language = "en"
            recreate()
            binging.eng.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        }

        binging.italiano.setOnClickListener {
            Settings.language = "it"
            recreate()
        }

        binging.back.setOnClickListener {
            onBackPressed();
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        val newConfiguration = Configuration(newBase?.resources?.configuration).apply {
            setLocale(Locale(Settings.language))

        }

        super.attachBaseContext(newBase?.createConfigurationContext(newConfiguration))
    }


    fun setLocale(languageCode: String, context: Context) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)

        // Riavvia l'Activity per applicare il cambio di lingua
        if (context is Activity) {
            context.recreate()
        }
    }
}