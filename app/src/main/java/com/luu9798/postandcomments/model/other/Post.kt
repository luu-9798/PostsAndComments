package com.luu9798.postandcomments.model.other

import com.luu9798.postandcomments.database.`object`.PostRealm
import com.luu9798.postandcomments.model.card.PostCard

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) {
    fun toRealm(): PostRealm {
        return PostRealm(
            userId = userId,
            id = id,
            title = title,
            body = body
        )
    }

    fun toPostCard(): PostCard {
        return PostCard(
            id = id,
            title = title,
            body = body
        )
    }
}
