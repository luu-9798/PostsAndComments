package com.luu9798.postandcomments.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}
