package com.luu9798.postandcomments.model.card

data class PostCard (
    val id: Int,
    val title: String,
    val body: String,
    var comments: List<CommentCard> = listOf()
)
