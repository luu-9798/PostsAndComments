package com.luu9798.postandcomments.view.adapter

import com.luu9798.postandcomments.model.card.UserCard
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserCardAdapterTest {

    private lateinit var userCardAdapter: UserCardAdapter
    private lateinit var userCardList: ArrayList<UserCard>

    @Before
    fun setUp() {
        userCardList = arrayListOf(
            UserCard(id = 1, name = "John Doe", email = "john@example.com", phone = "123 456-789", website = "johndoe.com", posts = arrayListOf()),
            UserCard(id = 2, name = "Jane Doe", email = "jane@example.com", phone = "123 456-789", website = "janedoe.com", posts = arrayListOf())
        )
        userCardAdapter = UserCardAdapter(userCardList)
    }

    @Test
    fun `getItemCount returns the correct size`() {
        assertEquals(userCardList.size, userCardAdapter.itemCount)
    }

    @Test
    fun `addAll adds new items`() {
        val newCardList = arrayListOf(
            UserCard(id = 3, name = "James Smith", email = "james@example.com", phone = "123 456-789", website = "jamessmith.com", posts = arrayListOf())
        )
        val expected = userCardList.size + newCardList.size
        userCardAdapter.addAll(newCardList)
        assertEquals(expected, userCardAdapter.itemCount)
    }
}
