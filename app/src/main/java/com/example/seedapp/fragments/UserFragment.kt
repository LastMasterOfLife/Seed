package com.example.seedapp.fragments


import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.seedapp.R
import com.google.android.material.button.MaterialButton


class UserFragment : Fragment() {


    private lateinit var imgProfilo: ImageView
    private lateinit var usernameText: TextView
    private lateinit var bioText: TextView
    private lateinit var btn1: MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)


        imgProfilo = view.findViewById(R.id.ImgProfilo)
        usernameText = view.findViewById(R.id.username)
        bioText = view.findViewById(R.id.bio)
        btn1 = view.findViewById(R.id.btn1)


        // Listener per il bottone
        btn1.setOnClickListener {
            showEditOptions()
        }


        return view
    }


    // Funzione per aprire un dialogo di scelta delle opzioni
    private fun showEditOptions() {
        val options = arrayOf("Modifica Immagine Profilo", "Modifica Username", "Modifica Bio")
        AlertDialog.Builder(requireContext())
            .setTitle("Scegli cosa modificare")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> chooseImage()
                    1 -> editUsername()
                    2 -> editBio()
                }
            }
            .show()
    }


    // Funzione per selezionare una nuova immagine dalla galleria
    private val chooseImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imgProfilo.setImageURI(uri)
        }
    }


    private fun chooseImage() {
        chooseImageLauncher.launch("image/*")
    }


    // Funzione per modificare lo username
    private fun editUsername() {
        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(usernameText.text.toString())
        }


        AlertDialog.Builder(requireContext())
            .setTitle("Modifica Username")
            .setView(editText)
            .setPositiveButton("Salva") { _, _ ->
                usernameText.text = editText.text.toString()
            }
            .setNegativeButton("Annulla", null)
            .show()
    }


    // Funzione per modificare la bio
    private fun editBio() {
        val editText = EditText(requireContext()).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            setText(bioText.text.toString())
        }


        AlertDialog.Builder(requireContext())
            .setTitle("Modifica Bio")
            .setView(editText)
            .setPositiveButton("Salva") { _, _ ->
                bioText.text = editText.text.toString()
            }
            .setNegativeButton("Annulla", null)
            .show()
    }
}
