package com.luu9798.postandcomments.database.`object`

import com.luu9798.postandcomments.model.other.Post
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PostRealm(
    var userId: Int = 0,
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var body: String = ""
): RealmObject() {
    fun toPost(): Post {
        return Post(
            userId = userId,
            id = id,
            title = title,
            body = body
        )
    }
}