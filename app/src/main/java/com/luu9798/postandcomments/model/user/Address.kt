package com.luu9798.postandcomments.model.user

import com.luu9798.postandcomments.database.`object`.AddressRealm

data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
) {
    fun toRealm(): AddressRealm {
        return AddressRealm(
            street = street,
            suite = suite,
            city = city,
            zipcode = zipcode,
            geo = geo.toRealm()
        )
    }
}
