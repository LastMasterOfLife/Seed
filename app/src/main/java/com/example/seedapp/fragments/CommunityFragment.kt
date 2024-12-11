package com.example.seedapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.seedapp.R

class CommunityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Data class per gestire i commenti e le risposte
        data class Comment(
            val text: String,
            val replies: MutableList<Comment> = mutableListOf(),
            var isRepliesVisible: Boolean = true
        )

        // Variabili di stato per i like
        var isLiked = false

        // Elementi UI principali
        val sendButton: Button = view.findViewById(R.id.sendButton)
        val likeIcon: ImageView? = view.findViewById(R.id.likeIcon)
        val replyIcon: ImageView = view.findViewById(R.id.reply_icon)
        val replyEditText: EditText = view.findViewById(R.id.replyEditText)
        val replyContainer: LinearLayout = view.findViewById(R.id.replyContainer)
        val repliesContainer: LinearLayout = view.findViewById(R.id.repliesContainer)

        // Inizializza le icone
        likeIcon?.setImageResource(R.drawable.like_icon)

        // Gestione del like sulla prima icona
        likeIcon?.setOnClickListener {
            isLiked = !isLiked
            likeIcon.setImageResource(if (isLiked) R.drawable.like_icon2 else R.drawable.like_icon)
        }

        // Mostra il contenitore per rispondere
        replyIcon.setOnClickListener {
            showReplyContainer(replyContainer, replyEditText)
        }

        // Invio del commento
        sendButton.setOnClickListener {
            val userText = replyEditText.text.toString()
            if (userText.isNotEmpty()) {
                addReply(view, repliesContainer, replyEditText, replyContainer, userText)
            }
        }

        return view
    }

    private fun showReplyContainer(replyContainer: LinearLayout, replyEditText: EditText) {
        replyContainer.visibility = View.VISIBLE
        replyEditText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(replyEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideReplyContainer(view: View, replyEditText: EditText, replyContainer: LinearLayout) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        replyEditText.text.clear()
        replyContainer.visibility = View.GONE
    }

    private fun addReply(
        view: View,
        repliesContainer: LinearLayout,
        replyEditText: EditText,
        replyContainer: LinearLayout,
        userText: String
    ) {
        // Crea una nuova vista di risposta
        val replyView = LayoutInflater.from(requireContext()).inflate(R.layout.reply_item, repliesContainer, false)

        val replyIconView: ImageView = replyView.findViewById(R.id.reply_icon)
        val likeIconView: ImageView = replyView.findViewById(R.id.likeIcon)
        val profileIcon: ImageView = replyView.findViewById(R.id.ohmaIcon)
        val replyTextView: TextView = replyView.findViewById(R.id.replyTextView)

        // Imposta testo e immagine profilo
        replyTextView.text = userText
        profileIcon.setImageResource(R.drawable.ohma_tokita)

        // Gestione like per la risposta
        var isLiked = false
        likeIconView.setOnClickListener {
            isLiked = !isLiked
            likeIconView.setImageResource(if (isLiked) R.drawable.like_icon2 else R.drawable.like_icon)
        }

        // Gestione della risposta alla risposta
        replyIconView.setOnClickListener {
            showReplyContainer(replyContainer, replyEditText)
        }

        // Aggiungi la risposta al contenitore
        repliesContainer.addView(replyView)

        // Nascondi la tastiera e il contenitore di risposta
        hideReplyContainer(view, replyEditText, replyContainer)
    }
}
