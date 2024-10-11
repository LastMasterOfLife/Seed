package com.example.seedapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityPrivacyBinding
import com.example.seedapp.databinding.FragmentSettingsBinding
import com.example.seedapp.fragments.HomeFragment
import com.example.seedapp.fragments.SeedFragment
import com.example.seedapp.fragments.SettingsFragment

class PrivacyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed();
        }

    }


}