package com.luu9798.postandcomments.model.user

import com.google.gson.Gson
import com.luu9798.postandcomments.database.`object`.UserRealm
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.model.other.Post


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address? = null,
    val phone: String,
    val website: String,
    val company: Company? = null
) {
    fun toRealm(): UserRealm {
        val gson = Gson()
        return UserRealm(
            id = id,
            name = name,
            username = username,
            email = email,
            address = gson.toJson(address),
            phone = phone,
            website = website,
            company = gson.toJson(company)
        )
    }

    fun toUserCard(): UserCard {
        return UserCard(
            id = id,
            name = name,
            username = username,
            email = email,
            phone = phone,
            website = website
        )
    }
}
