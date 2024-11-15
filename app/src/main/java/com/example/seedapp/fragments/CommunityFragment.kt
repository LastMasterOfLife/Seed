package com.example.seedapp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.seedapp.R


import com.google.android.material.internal.ViewUtils.showKeyboard


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommunityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var isLiked = false
        var isLiked2 = false
        val view = inflater.inflate(R.layout.fragment_community, container, false)
        val sendButton: Button = view.findViewById(R.id.sendButton)
        val likeIcon: ImageView? = view.findViewById(R.id.likeIcon)
        val replyIcon: ImageView = view.findViewById(R.id.reply_icon)
        val replyTextView: TextView = view.findViewById(R.id.replyTextView)
        val replyEditText: EditText = view.findViewById(R.id.replyEditText)
        val replyContainer: LinearLayout = view.findViewById(R.id.replyContainer)
        val repliesContainer: LinearLayout = view.findViewById(R.id.repliesContainer)








// Inizializza le icone con l'immagine vuota
        likeIcon?.setImageResource(R.drawable.like_icon)  // icona vuota




// Click listener per la prima icona
        likeIcon?.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeIcon.setImageResource(R.drawable.like_icon2)  // icona colorata/piena
            } else {
                likeIcon.setImageResource(R.drawable.like_icon)   // icona vuota
            }
        }






        // Click listener per la reply icon
        replyIcon.setOnClickListener {
            // Mostra il layout del contenitore con EditText e profilo
            replyContainer.visibility = View.VISIBLE
            replyEditText.requestFocus()


            // Mostra la tastiera
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(replyEditText, InputMethodManager.SHOW_IMPLICIT)
        }


        // Listener per l'EditText: nasconde il layout al clic su "Done"
        // Click listener per il bottone di invio
        sendButton.setOnClickListener {
            val userText = replyEditText.text.toString()


            // Crea un nuovo layout per mostrare l'immagine profilo e il testo
            val replyView = LayoutInflater.from(requireContext()).inflate(R.layout.reply_item, repliesContainer, false)


            val ohmaIcon: ImageView = replyView.findViewById(R.id.ohmaIcon)
            val replyTextView: TextView = replyView.findViewById(R.id.replyTextView)


            // Imposta il testo e l'immagine del profilo (usa l'immagine di profilo desiderata)
            replyTextView.text = userText
            ohmaIcon.setImageResource(R.drawable.ohma_tokita)  // Sostituisci con l'immagine del profilo desiderata


            // Aggiungi la nuova vista di risposta a repliesContainer
            repliesContainer.addView(replyView)


            // Nascondi la tastiera e pulisci l'EditText
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            replyEditText.text.clear()
            replyContainer.visibility = View.GONE
        }


        return view
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommunityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
