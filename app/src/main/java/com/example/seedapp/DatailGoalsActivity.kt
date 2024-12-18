package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityDatailGoalsBinding

class DatailGoalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatailGoalsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatailGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBranchButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}