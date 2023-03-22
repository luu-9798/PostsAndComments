package com.luu9798.postandcomments.database.`object`

import com.google.gson.Gson
import com.luu9798.postandcomments.model.user.Address
import com.luu9798.postandcomments.model.user.Company
import com.luu9798.postandcomments.model.user.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var address: String = "",
    var phone: String = "",
    var website: String = "",
    var company: String = ""
) : RealmObject() {
    fun toUser(): User {
        val gson = Gson()
        return User(
            id = id,
            name = name,
            username = username,
            email = email,
            address = gson.fromJson(address, Address::class.java),
            phone = phone,
            website = website,
            company = gson.fromJson(company, Company::class.java)
        )
    }
}