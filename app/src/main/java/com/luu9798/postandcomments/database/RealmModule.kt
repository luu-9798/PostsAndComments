package com.luu9798.postandcomments.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RealmModule {

    @Provides
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }
}
