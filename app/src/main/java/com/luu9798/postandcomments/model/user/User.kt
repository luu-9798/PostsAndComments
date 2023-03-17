package com.luu9798.postandcomments.model.user

import com.luu9798.postandcomments.database.`object`.UserRealm


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) {
    fun toRealm(): UserRealm {
        return UserRealm(
            id = id,
            name = name,
            username = username,
            email = email,
            address = address.toRealm(),
            phone = phone,
            website = website,
            company = company.toRealm()
        )
    }
}
