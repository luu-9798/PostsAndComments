package com.luu9798.postandcomments

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class PostsAndCommentsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
