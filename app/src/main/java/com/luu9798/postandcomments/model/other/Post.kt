package com.luu9798.postandcomments.model.other

import com.luu9798.postandcomments.database.`object`.PostRealm

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
}
