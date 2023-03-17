package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject

open class AddressRealm(
    var street: String = "",
    var suite: String = "",
    var city: String = "",
    var zipcode: String = "",
    var geo: GeoRealm? = GeoRealm()
): RealmObject()
