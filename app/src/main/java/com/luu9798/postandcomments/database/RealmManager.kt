package com.luu9798.postandcomments.database

import io.realm.Realm

class RealmManager {
    private val realm: ThreadLocal<Realm> = ThreadLocal()

    fun getRealmInstance(): Realm {
        var instance = realm.get()
        if (instance == null || instance.isClosed) {
            instance = Realm.getDefaultInstance()
            realm.set(instance)
        }
        return instance
    }

    fun closeRealmInstance() {
        realm.get()?.apply {
            if (!isClosed) {
                close()
            }
        }
        realm.remove()
    }
}
