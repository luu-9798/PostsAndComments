package com.luu9798.postandcomments.view.adapter

import com.luu9798.postandcomments.model.card.UserCard
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {
    @Provides
    fun provideUserCardAdapter(): UserCardAdapter {
        val userCardList = arrayListOf<UserCard>()
        return UserCardAdapter(userCardList)
    }
}