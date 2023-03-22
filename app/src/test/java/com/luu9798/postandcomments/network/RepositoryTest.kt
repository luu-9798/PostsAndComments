package com.luu9798.postandcomments.network

import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var service: Service

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getUsers returns list of users`() = runTest {
        // Given
        val expectedUsers = listOf(
            User(
                id = 1,
                name = "John Doe",
                username = "john.doe",
                email = "john@example.com",
                phone = "123 456-789",
                website = "johndoe.com"
            ),
            User(2,
                name = "Jane Doe",
                username = "jane.doe",
                email = "jane@example.com",
                phone = "123 456-789",
                website = "janedoe.com"
            )
        )
        `when`(retrofit.create(Service::class.java)).thenReturn(service)
        `when`(service.getUsers()).thenReturn(expectedUsers)

        // When
        val repository = Repository(retrofit)
        val actualUsers = repository.getUsers()

        // Then
        assertEquals(expectedUsers, actualUsers)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getPosts returns list of post`() = runTest {
        // Given
        val expectedPosts = listOf(
            Post(1, 1, "First Post", "This is the first post"),
            Post(2, 1, "Second Post", "This is the second post")
        )
        `when`(retrofit.create(Service::class.java)).thenReturn(service)
        `when`(service.getPosts()).thenReturn(expectedPosts)

        // When
        val repository = Repository(retrofit)
        val actualPosts = repository.getPosts()

        // Then
        assertEquals(expectedPosts, actualPosts)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getComments returns list of comments`() = runTest {
        // Given
        val expectedComments = listOf(
            Comment(1, 1, "John Doe", "john@example.com", "This is a comment"),
            Comment(2, 1, "Jane Doe", "jane@example.com", "This is another comment")
        )
        `when`(retrofit.create(Service::class.java)).thenReturn(service)
        `when`(service.getComments()).thenReturn(expectedComments)

        // When
        val repository = Repository(retrofit)
        val actualComments = repository.getComments()

        // Then
        assertEquals(expectedComments, actualComments)
    }
}
