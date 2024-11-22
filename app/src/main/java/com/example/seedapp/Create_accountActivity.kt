package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seedapp.databinding.ActivityCreateAccountBinding

class Create_accountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgFB.setOnClickListener {
            loginWithFacebook()
        }

        binding.imgGoogle.setOnClickListener {
            loginWithGoogle()
        }

        binding.creaAccount.setOnClickListener {
            createNewAccount()

        }
    }

    private fun loginWithFacebook() {
        Toast.makeText(this, "Accesso con Facebook non implementato", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithGoogle() {
        Toast.makeText(this, "Accesso con Google non implementato", Toast.LENGTH_SHORT).show()
    }

    private fun createNewAccount() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}