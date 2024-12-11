package com.example.seedapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seedapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        changeStatusBarColor()


        binding.imgFB.setOnClickListener {
            loginWithFacebook()
        }


        binding.imgGoogle.setOnClickListener {
            loginWithGoogle()
        }


        binding.create.setOnClickListener {
            goToCreateAccount()
        }


        binding.login.setOnClickListener {
            val user = binding.username.text.toString()
            val password = binding.password.text.toString()


            if (checkUser(user)) {
                if (checkPassword(password)) {
                    login()
                } else {
                    Toast.makeText(
                        this,
                        "La Password non contiene almeno una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun changeStatusBarColor() {
        window.statusBarColor = resources.getColor(R.color.white) // Sostituisci con il colore desiderato
    }


    private fun checkPassword(password: String): Boolean {
        val uppercaseRegex = Regex(".*[A-Z].*")
        val lowercaseRegex = Regex(".*[a-z].*")
        val specialCharRegex = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")
        val numberRegex = Regex(".*[0-9].*")


        return password.isNotEmpty() &&
                uppercaseRegex.containsMatchIn(password) &&
                lowercaseRegex.containsMatchIn(password) &&
                specialCharRegex.containsMatchIn(password) &&
                numberRegex.containsMatchIn(password)
    }


    private fun checkUser(user: String): Boolean {
        return if (user.isEmpty()) {
            Toast.makeText(this, "Manca lo username", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }


    private fun loginWithFacebook() {
        Toast.makeText(this, "Accesso con Facebook non implementato", Toast.LENGTH_SHORT).show()
    }


    private fun loginWithGoogle() {
        Toast.makeText(this, "Accesso con Google non implementato", Toast.LENGTH_SHORT).show()
    }


    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun goToCreateAccount() {
        val intent = Intent(this, Create_accountActivity::class.java)
        startActivity(intent)
    }
}
