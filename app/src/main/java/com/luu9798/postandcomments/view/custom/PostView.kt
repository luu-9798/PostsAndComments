package com.luu9798.postandcomments.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.luu9798.postandcomments.R
import com.luu9798.postandcomments.databinding.ViewPostBinding
import com.luu9798.postandcomments.model.card.PostCard

class PostView @JvmOverloads constructor(
    context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewPostBinding =
        ViewPostBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.dropDownArrowPost.setOnClickListener {
            binding.dropDownArrowPost.toggleArrow()
            binding.linearLayoutComment.visibility = if (binding.linearLayoutComment.isVisible) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    fun setPostCard(postCard: PostCard) {
        binding.textViewPostTitle.text = resources.getString(R.string.title, postCard.title)
        binding.textViewPostBody.text = postCard.body
        for (comment in postCard.comments) {
            val commentView = CommentView(this.context)
            commentView.setCommentCard(comment)
            binding.linearLayoutComment.addView(commentView)
        }
        // Set other views as needed, e.g., for comments
    }
}
