package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject

open class CompanyRealm(
    var name: String = "",
    var catchPhrase: String = "",
    var bs: String = ""
): RealmObject()
