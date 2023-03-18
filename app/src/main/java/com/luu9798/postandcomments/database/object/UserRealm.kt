package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var address: AddressRealm? = AddressRealm(),
    var phone: String = "",
    var website: String = "",
    var company: CompanyRealm? = CompanyRealm()
) : RealmObject()