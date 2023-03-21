package com.luu9798.postandcomments.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luu9798.postandcomments.databinding.ViewHolderUserBinding
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.view.custom.PostView

class UserCardAdapter(private val cardList: ArrayList<UserCard>): RecyclerView.Adapter<UserCardAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ViewHolderUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userCard: UserCard) {
            binding.apply {
                userName.text = userCard.name
                userEmail.text = userCard.email
                userPhone.text = userCard.phone
                userWebsite.text = userCard.website

                dropDownArrow.setOnClickListener {
                    if (userInfo.visibility == View.VISIBLE) {
                        userInfo.visibility = View.GONE
                        dividerUserInfo.visibility = View.GONE
                        dropDownArrow.toggleArrow()
                    } else {
                        userInfo.visibility = View.VISIBLE
                        dividerUserInfo.visibility = View.VISIBLE
                        dropDownArrow.toggleArrow()
                    }
                }

                for (post in userCard.posts) {
                    val postView = PostView(this.root.context)
                    postView.setPostCard(post)
                    linearLayoutPost.addView(postView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int = cardList.size

    fun addAll(newCardList: List<UserCard>) {
        cardList.addAll(newCardList)
    }
}
