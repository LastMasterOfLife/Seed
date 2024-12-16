package com.example.seedapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.seedapp.R


class CommunityFragment : Fragment() {


    private lateinit var replyIcon: ImageView
    private lateinit var replyContainer: LinearLayout
    private lateinit var replyEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var repliesContainer: LinearLayout
    private lateinit var likeIcon: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)


        // Inizializza gli elementi UI
        replyIcon = view.findViewById(R.id.reply_icon)
        replyContainer = view.findViewById(R.id.replyContainer)
        replyEditText = view.findViewById(R.id.replyEditText)
        sendButton = view.findViewById(R.id.sendButton)
        repliesContainer = view.findViewById(R.id.repliesContainer)
        likeIcon = view.findViewById(R.id.likeIcon)


        var isLiked = false


        // Logica per alternare il contenitore di risposta
        replyIcon.setOnClickListener {
            toggleVisibility(replyContainer)
        }


        // Logica per inviare una risposta
        sendButton.setOnClickListener {
            sendReply()
        }


        // Gestione del like
        likeIcon.setImageResource(R.drawable.like_icon)
        likeIcon.tag = "not_liked"


        likeIcon.setOnClickListener {
            isLiked = toggleLikeIcon(likeIcon, isLiked)
        }


        return view
    }


    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
    }


    private fun sendReply() {
        val replyText = replyEditText.text.toString().trim()


        if (replyText.isNotBlank()) {
            // Crea una nuova view per il commento direttamente nel repliesContainer
            val commentView = layoutInflater.inflate(R.layout.reply_item, repliesContainer, false)


            val toggleIcon: ImageView = commentView.findViewById(R.id.toggleIcon)
            val replyIcon: ImageView = commentView.findViewById(R.id.replyIcon)
            val replyTextView: TextView = commentView.findViewById(R.id.replyTextView)
            val subCommentInputContainer: LinearLayout = commentView.findViewById(R.id.subCommentInputContainer)
            val subCommentEditText: EditText = commentView.findViewById(R.id.subCommentEditText)
            val sendSubCommentButton: Button = commentView.findViewById(R.id.sendSubCommentButton)
            val subCommentContainer: LinearLayout = commentView.findViewById(R.id.subCommentContainer)


            // Imposta il testo del commento
            replyTextView.text = replyText


            // Toggle visibilità dei sotto-commenti
            toggleIcon.setOnClickListener {
                toggleVisibility(subCommentContainer)
                toggleIcon.setImageResource(if (subCommentContainer.visibility == View.VISIBLE) R.drawable.minus_icon else R.drawable.plus_icon)
            }


            // Mostra input per sotto-commenti
            replyIcon.setOnClickListener {
                toggleVisibility(subCommentInputContainer)
            }


            // Invia sotto-commento
            sendSubCommentButton.setOnClickListener {
                val subCommentText = subCommentEditText.text.toString().trim()
                if (subCommentText.isNotBlank()) {
                    addSubComment(subCommentContainer, subCommentText)
                    subCommentEditText.text.clear()
                    subCommentInputContainer.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Scrivi un sotto-commento", Toast.LENGTH_SHORT).show()
                }
            }


            // Aggiungi il commento al container dei commenti nel fragment_community
            repliesContainer.addView(commentView)


            // Resetta l'input e nascondi il container di risposta
            replyEditText.text.clear()
            replyContainer.visibility = View.GONE
        } else {
            Toast.makeText(requireContext(), "Scrivi qualcosa prima di inviare", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addSubComment(parentContainer: LinearLayout, subCommentText: String) {
        val subCommentView = layoutInflater.inflate(R.layout.comment_item, parentContainer, false)


        val subCommentTextView: TextView = subCommentView.findViewById(R.id.subCommentTextView)
        //val toggleSubCommentIcon: ImageView = subCommentView.findViewById(R.id.toggleSubCommentIcon)
        val subCommentReplyIcon: ImageView = subCommentView.findViewById(R.id.replyIcon)


        subCommentTextView.text = subCommentText


        // Opzionale: Aggiungi logica per il sotto-commento del sotto-commento
        subCommentReplyIcon.setOnClickListener {
            // Puoi implementare una logica per gestire risposte nidificate
            Toast.makeText(requireContext(), "Funzionalità di risposta nidificata", Toast.LENGTH_SHORT).show()
        }


        parentContainer.addView(subCommentView)


        // Assicurati che il contenitore dei sotto-commenti sia visibile
        parentContainer.visibility = View.VISIBLE
    }


    private fun toggleLikeIcon(icon: ImageView, currentState: Boolean): Boolean {
        return if (currentState) {
            icon.setImageResource(R.drawable.like_icon)
            icon.tag = "not_liked"
            false
        } else {
            icon.setImageResource(R.drawable.like_icon2)
            icon.tag = "liked"
            true
        }
    }
}
