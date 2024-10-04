package com.example.seedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seedapp.databinding.ActivityMainBinding
import com.example.seedapp.fragments.CommunityFragment
import com.example.seedapp.fragments.HomeFragment
import com.example.seedapp.fragments.SeedFragment
import com.example.seedapp.fragments.SettingsFragment
import com.example.seedapp.fragments.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set default fragment
        supportFragmentManager.beginTransaction().replace(binding.container.id, SeedFragment()).commit()

        // default icon Navigationbar
        bottomNavigationView.selectedItemId = R.id.seed

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.game -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                    true
                }
                R.id.community -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, CommunityFragment()).commit()
                    true
                }
                R.id.seed -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SeedFragment()).commit()
                    true
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, UserFragment()).commit()
                    true
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}