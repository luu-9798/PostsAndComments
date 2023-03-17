package com.luu9798.postandcomments.model.user

import com.luu9798.postandcomments.database.`object`.GeoRealm

data class Geo(
    val lat: String,
    val lng: String
) {
    fun toRealm(): GeoRealm {
        return GeoRealm(
            lat = lat,
            lng = lng
        )
    }
}
