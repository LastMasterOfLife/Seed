package com.example.seedapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
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

        val likeIcon: ImageView? = view.findViewById(R.id.likeIcon)
        val replyIcon: ImageView = view.findViewById(R.id.reply_icon)
        val textView: TextView = view.findViewById(R.id.textView3)
        val hiddenEditText: EditText = view.findViewById(R.id.hiddenEditText)
        val replyIcon2: ImageView = view.findViewById(R.id.reply_icon2)
        val likeIcon2: ImageView? = view.findViewById(R.id.like_icon2)



// Inizializza le icone con l'immagine vuota
        likeIcon?.setImageResource(R.drawable.like_icon)  // icona vuota
        likeIcon2?.setImageResource(R.drawable.like_icon) // icona vuota

// Click listener per la prima icona
        likeIcon?.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeIcon.setImageResource(R.drawable.like_icon2)  // icona colorata/piena
            } else {
                likeIcon.setImageResource(R.drawable.like_icon)   // icona vuota
            }
        }
        replyIcon.setOnClickListener {
            showKeyboardAndAddText(hiddenEditText, textView)
        }

// Click listener per la seconda icona
        likeIcon2?.setOnClickListener {
            isLiked2 = !isLiked2
            if (isLiked2) {
                likeIcon2.setImageResource(R.drawable.like_icon2) // icona colorata/piena
            } else {
                likeIcon2.setImageResource(R.drawable.like_icon)  // icona vuota
            }
        }

        return view
    }
    private fun showKeyboardAndAddText(editText: EditText, textView: TextView) {
        // Mostra la tastiera usando l'EditText nascosto
        editText.visibility = View.VISIBLE
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

        // Aggiorna il testo della TextView
        textView.text = editText.text.toString()
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

