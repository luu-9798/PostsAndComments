package com.luu9798.postandcomments.network

import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import retrofit2.Retrofit

class Repository(retrofit: Retrofit): Service {
    private val retrofit = retrofit.create(Service::class.java)

    override suspend fun getUsers(): List<User> {
        return retrofit.getUsers()
    }

    override suspend fun getPosts(): List<Post> {
        return retrofit.getPosts()
    }

    override suspend fun getComments(): List<Comment> {
        return retrofit.getComments()
    }
}
