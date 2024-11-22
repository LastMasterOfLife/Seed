package com.example.seedapp.fragments

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Alignment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seedapp.Data.GoalsAdapter
import com.example.seedapp.Data.Item
import com.example.seedapp.R
import com.google.android.material.button.MaterialButton

class UserFragment : Fragment() {

    private lateinit var imgProfilo: ImageView
    private lateinit var usernameText: TextView
    private lateinit var bioText: TextView
    private lateinit var codID: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        imgProfilo = view.findViewById(R.id.ImgProfilo)
        usernameText = view.findViewById(R.id.username)
        bioText = view.findViewById(R.id.bio)
        codID = view.findViewById<TextView>(R.id.TextCodId)
        val changeUser = view.findViewById<ImageButton>(R.id.modUser)

        // Dati di esempio
        val items = listOf(
            Item("Obbiettivo 1"),
            Item("Obbiettivo 2"),
            Item("Obbiettivo 3"),
            Item("obbiettivo 4"),
            Item("Obbiettivo 5"),
            Item("obbiettivo 6"),
        )

        // Trova la RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.listaOb)

        // Configura il LayoutManager e l'Adapter
        recyclerView.layoutManager = LinearLayoutManager(context,1,false)
        recyclerView.adapter = GoalsAdapter(items)

        changeUser.setOnClickListener {
            chooseImage()
        }

        //val videoButton = view.findViewById<VideoView>(R.id.videoButton)
        val imageButton = view.findViewById<ImageButton>(R.id.linkbtn)

        imageButton.setOnClickListener {
            // Codice per copiare il testo negli appunti
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", codID.text)
            clipboard.setPrimaryClip(clip)

            // Mostra il Toast
            val toast = Toast.makeText(requireContext(), "Copiato", Toast.LENGTH_SHORT)
            toast.setGravity(android.view.Gravity.BOTTOM or android.view.Gravity.CENTER_HORIZONTAL, 0, 100)
            toast.show()
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
