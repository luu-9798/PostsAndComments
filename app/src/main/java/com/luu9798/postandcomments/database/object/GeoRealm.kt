package com.luu9798.postandcomments.database.`object`

import io.realm.RealmObject

open class GeoRealm(
    var lat: String = "",
    var lng: String = ""
): RealmObject()