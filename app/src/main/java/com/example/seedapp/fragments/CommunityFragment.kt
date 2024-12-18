package com.example.seedapp.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.seedapp.R


class CommunityFragment : Fragment() {


    private lateinit var elements: List<ReplyComponent>




    data class ReplyComponent(
        val replyIcon: ImageView,
        val replyContainer: LinearLayout,
        val replyEditText: EditText,
        val sendButton: Button,
        val likeIcon: ImageView,
        val repliesContainer: LinearLayout,
        var isLiked: Boolean = false
    )




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)






        elements = listOf(
            ReplyComponent(
                view.findViewById(R.id.reply_icon),
                view.findViewById(R.id.replyContainer),
                view.findViewById(R.id.replyEditText),
                view.findViewById(R.id.sendButton),
                view.findViewById(R.id.likeIcon),
                view.findViewById(R.id.repliesContainer)


            ),
            ReplyComponent(
                view.findViewById(R.id.reply_icon2),
                view.findViewById(R.id.replyContainer2),
                view.findViewById(R.id.replyEditText2),
                view.findViewById(R.id.sendButton2),
                view.findViewById(R.id.likeIcon2),
                view.findViewById(R.id.repliesContainer2),


                ),
            ReplyComponent(
                view.findViewById(R.id.reply_icon3),
                view.findViewById(R.id.replyContainer3),
                view.findViewById(R.id.replyEditText3),
                view.findViewById(R.id.sendButton3),
                view.findViewById(R.id.likeIcon3),
                view.findViewById(R.id.repliesContainer3),


                )
        )


        elements.forEach { component ->
            component.replyIcon.setOnClickListener {
                toggleVisibility(component.replyContainer)
            }


            component.sendButton.setOnClickListener {
                sendReply(component)
            }
            component.likeIcon.setOnClickListener {
                toggleLike(component)
            }


        }


        return view
    }


    private fun toggleLike(component: ReplyComponent) {
        component.isLiked = !component.isLiked
        if (component.isLiked) {
            component.likeIcon.setImageResource(R.drawable.like_icon2)


        } else {
            component.likeIcon.setImageResource(R.drawable.like_icon)


        }
    }


    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
    }


    private fun sendReply(component: ReplyComponent) {
        val replyText = component.replyEditText.text.toString().trim()


        if (replyText.isNotBlank()) {
            // Crea una nuova view per il commento direttamente nel repliesContainer
            val commentView = layoutInflater.inflate(R.layout.reply_item, component.repliesContainer, false)


            val plusIcon: ImageView = commentView.findViewById(R.id.toggleIcon)
            val likeIcon: ImageView = commentView.findViewById(R.id.likeIcon)
            val replyIcon: ImageView = commentView.findViewById(R.id.replyIcon)
            val replyTextView: TextView = commentView.findViewById(R.id.replyTextView)
            val subCommentInputContainer: LinearLayout = commentView.findViewById(R.id.subCommentInputContainer)
            val subCommentEditText: EditText = commentView.findViewById(R.id.subCommentEditText)
            val sendSubCommentButton: Button = commentView.findViewById(R.id.sendSubCommentButton)
            val subCommentContainer: LinearLayout = commentView.findViewById(R.id.subCommentContainer)


            var isLiked= false


            likeIcon?.setOnClickListener {
                isLiked = !isLiked
                if (isLiked) {
                    likeIcon.setImageResource(R.drawable.like_icon2)  // icona colorata/piena
                } else {
                    likeIcon.setImageResource(R.drawable.like_icon)   // icona vuota
                }
            }

            // Imposta il testo del commento
            replyTextView.text = replyText


            // Toggle visibilità dei sotto-commenti
            plusIcon.setOnClickListener {
                toggleVisibility(subCommentContainer)
                plusIcon.setImageResource(if (subCommentContainer.visibility == View.VISIBLE) R.drawable.minus_icon else R.drawable.plus_icon)
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
            component.repliesContainer.addView(commentView)


            // Resetta l'input e nascondi il container di risposta
            component.replyEditText.text.clear()
            component.replyContainer.visibility = View.GONE
        } else {
            Toast.makeText(requireContext(), "Scrivi qualcosa prima di inviare", Toast.LENGTH_SHORT).show()
        }
    }


    private fun addSubComment(parentContainer: LinearLayout, subCommentText: String) {
        val subCommentView = layoutInflater.inflate(R.layout.comment_item, parentContainer, false)
        val likeIcon : ImageView = subCommentView.findViewById(R.id.likeIcon)
        val subCommentTextView: TextView = subCommentView.findViewById(R.id.subCommentTextView)
        //val plusIcon: ImageView = subCommentView.findViewById(R.id.toggleIcon)
        val subCommentReplyIcon: ImageView = subCommentView.findViewById(R.id.replyIcon)


        var isLiked= false


        likeIcon?.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                likeIcon.setImageResource(R.drawable.like_icon2)  // icona colorata/piena
            } else {
                likeIcon.setImageResource(R.drawable.like_icon)   // icona vuota
            }
        }


        subCommentTextView.text = subCommentText


        // Opzionale: Aggiungi logica per il sotto-commento del sotto-commento
        subCommentReplyIcon.setOnClickListener {
            // Puoi implementare una logica per gestire risposte nidificate
            Toast.makeText(requireContext(), "Funzio    nalità di risposta nidificata", Toast.LENGTH_SHORT).show()
        }


        parentContainer.addView(subCommentView)


        // Assicurati che il contenitore dei sotto-commenti sia visibile
        parentContainer.visibility = View.VISIBLE
    }
}
