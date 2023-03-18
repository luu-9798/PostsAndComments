package com.luu9798.postandcomments.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.realm.Realm

@Module
@InstallIn(ViewModelComponent::class)
object RealmModule {
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}
