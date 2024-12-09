package com.example.seedapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.seedapp.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommunityFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var isContentVisible = true
    private var isPlusImage = true
    private var isLiked = false

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
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Views initialization
        val sendButton: Button = view.findViewById(R.id.sendButton)
        val likeIcon: ImageView? = view.findViewById(R.id.likeIcon)
        val replyIcon: ImageView = view.findViewById(R.id.reply_icon)
        val replyEditText: EditText = view.findViewById(R.id.replyEditText)
        val replyContainer: LinearLayout = view.findViewById(R.id.replyContainer)
        val repliesContainer: LinearLayout = view.findViewById(R.id.repliesContainer)

        // Initialize likeIcon with default empty image
        likeIcon?.setImageResource(R.drawable.like_icon)

        // Like icon click listener
        likeIcon?.setOnClickListener {
            isLiked = !isLiked
            likeIcon.setImageResource(
                if (isLiked) R.drawable.like_icon2 else R.drawable.like_icon
            )
        }

        // Reply icon click listener
        replyIcon.setOnClickListener {
            replyContainer.visibility = View.VISIBLE
            replyEditText.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(replyEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        // Send button click listener
        sendButton.setOnClickListener {
            val userText = replyEditText.text.toString()
            if (userText.isNotEmpty()) {
                addReplyView(userText, repliesContainer)
                hideKeyboardAndClearInput(view, replyEditText, replyContainer)
            }
        }

        return view
    }

    private fun addReplyView(userText: String, repliesContainer: LinearLayout) {
        val replyView = LayoutInflater.from(requireContext())
            .inflate(R.layout.reply_item, repliesContainer, false)

        val replyTextView: TextView = replyView.findViewById(R.id.replyTextView)
        val likeIconView: ImageView = replyView.findViewById(R.id.likeIcon)
        val ohmaIcon: ImageView = replyView.findViewById(R.id.ohmaIcon)
        val plusIcon: ImageView = replyView.findViewById(R.id.plusIcon)
        val replyIcon: ImageView = replyView.findViewById(R.id.replyIcon)

        replyTextView.text = userText
        ohmaIcon.setImageResource(R.drawable.ohma_tokita)

        // Reply icon click listener: Adds under comments dynamically
        replyIcon.setOnClickListener {
            addUnderComments("Under-comment for: $userText", repliesContainer)
        }

        // Like icon functionality for reply
        likeIconView.setOnClickListener {
            isLiked = !isLiked
            likeIconView.setImageResource(
                if (isLiked) R.drawable.like_icon2 else R.drawable.like_icon
            )
        }

        // Plus/minus icon functionality for nested comments
        plusIcon.setOnClickListener {
            isPlusImage = !isPlusImage
            plusIcon.setImageResource(
                if (isPlusImage) R.drawable.plus else R.drawable.minus
            )
        }

        repliesContainer.addView(replyView)
    }

    private fun addUnderComments(userText: String, repliesContainer: LinearLayout) {
        val underComments = LayoutInflater.from(requireContext())
            .inflate(R.layout.comment_item, repliesContainer, false)

        val replyTextView: TextView = underComments.findViewById(R.id.bakiText)
        val likeIconView: ImageView = underComments.findViewById(R.id.likeIcon)
        val bakiIcon: ImageView = underComments.findViewById(R.id.bakiIconView)
        val plusIcon: ImageView = underComments.findViewById(R.id.plusIcon)

        replyTextView.text = userText
        bakiIcon.setImageResource(R.drawable.baki_hanma)

        // Like icon functionality for under-comment
        likeIconView.setOnClickListener {
            isLiked = !isLiked
            likeIconView.setImageResource(
                if (isLiked) R.drawable.like_icon2 else R.drawable.like_icon
            )
        }

        // Plus/minus icon functionality for nested comments
        plusIcon.setOnClickListener {
            isPlusImage = !isPlusImage
            plusIcon.setImageResource(
                if (isPlusImage) R.drawable.plus else R.drawable.minus
            )
        }

        repliesContainer.addView(underComments)
    }

    private fun hideKeyboardAndClearInput(
        view: View,
        replyEditText: EditText,
        replyContainer: LinearLayout
    ) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        replyEditText.text.clear()
        replyContainer.visibility = View.GONE
    }

    companion object {
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
