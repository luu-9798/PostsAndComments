package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CommentRealm(
    var postId: Int = 0,
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var body: String = ""
): RealmObject()