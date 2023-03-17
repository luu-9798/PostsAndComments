package com.luu9798.postandcomments.model.other

import com.luu9798.postandcomments.database.`object`.CommentRealm

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
) {
    fun toRealm(): CommentRealm {
        return CommentRealm(
            postId = postId,
            id = id,
            name = name,
            email = email,
            body = body
        )
    }
}
