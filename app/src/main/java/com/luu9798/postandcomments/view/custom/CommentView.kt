package com.luu9798.postandcomments.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.luu9798.postandcomments.R
import com.luu9798.postandcomments.databinding.ViewCommentBinding
import com.luu9798.postandcomments.model.card.CommentCard

class CommentView @JvmOverloads constructor(
    context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCommentBinding =
        ViewCommentBinding.inflate(LayoutInflater.from(context), this, true)

    fun setCommentCard(commentCard: CommentCard) {
        binding.nameTextView.text = resources.getString(R.string.name, commentCard.name)
        binding.bodyTextView.text = commentCard.body
        // Set other views as needed, e.g., for comments
    }
}