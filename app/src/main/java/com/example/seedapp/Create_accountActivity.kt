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

        val name = binding.nomeIn.text
        val user = binding.nomeUtenteIn.text
        val password = binding.passwordIn.text

        binding.imgFB.setOnClickListener {
            loginWithFacebook()
        }

        binding.imggoogle.setOnClickListener {
            loginWithGoogle()
        }

        binding.creaAccount.setOnClickListener {
            if(checkName(name.toString())){
                if (checkUser(name.toString(),user.toString())){
                    if (checkPassword(password.toString())){
                        createNewAccount()
                    }
                    else{
                        Toast.makeText(this, "La Password non contiene almeno una lettera maiuscola, una lettera minuscola e un carattere speciale", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun checkPassword(password: String) : Boolean{

        // Regex per controllare i requisiti
        val uppercaseRegex = Regex(".*[A-Z].*") // Almeno una maiuscola
        val lowercaseRegex = Regex(".*[a-z].*") // Almeno una minuscola
        val specialCharRegex = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*") // Almeno un carattere speciale
        val numberRagex = Regex(".*[0-9].*") // almeno un numero


        if (password.isNotEmpty()){
            return uppercaseRegex.containsMatchIn(password) &&
                    lowercaseRegex.containsMatchIn(password) &&
                    specialCharRegex.containsMatchIn(password) &&
                    numberRagex.containsMatchIn(password)
        }
        return false
    }

    private fun checkUser(name: String, user: String) : Boolean{
        if (user.isNotEmpty() && name.isNotEmpty()) {
            if (name == user) {
                Toast.makeText(this, "Lo UserName non può essere uguale al nome", Toast.LENGTH_LONG)
                    .show()
                return false
            }
            else{
                return true
            }
        }
        else{
            Toast.makeText(this, "manca lo UserName", Toast.LENGTH_LONG)
                .show()
            return false
        }
    }

    private fun checkName(name: String) : Boolean{
        if (name.isEmpty()){
            Toast.makeText(this, "manca il nome", Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
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