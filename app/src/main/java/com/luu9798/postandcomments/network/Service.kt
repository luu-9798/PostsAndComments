package com.luu9798.postandcomments.network

import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import retrofit2.http.GET

interface Service {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>
}
