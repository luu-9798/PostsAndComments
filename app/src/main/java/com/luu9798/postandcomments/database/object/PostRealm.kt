package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PostRealm(
    var userId: Int = 0,
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var body: String = ""
): RealmObject()