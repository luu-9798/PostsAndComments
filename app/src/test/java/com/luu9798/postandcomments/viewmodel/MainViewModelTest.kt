package com.luu9798.postandcomments.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.luu9798.postandcomments.database.RealmManager
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import com.luu9798.postandcomments.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var realmManager: RealmManager

    @Mock
    private lateinit var repository: Repository

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(realmManager, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchDataAndMapCards fetches data from API and maps it to cards`() {
        runBlockingTest {
            // Prepare the test data
            val users = generateTestUsers()
            val posts = generateTestPosts()
            val comments = generateTestComments()

            // Mock the Repository's behavior
            `when`(repository.getUsers()).thenReturn(users)
            `when`(repository.getPosts()).thenReturn(posts)
            `when`(repository.getComments()).thenReturn(comments)

            // Set up an observer for the cards LiveData
            val cardsObserver = Observer<List<UserCard>> {}
            viewModel.cards.observeForever(cardsObserver)

            // Call the function to fetch data from the API and map it to cards
            viewModel.fetchDataAndMapCards(fetchFromAPI = true)

            // Verify that the fetched data is properly mapped to cards
            val expectedCards = mapToUserCards(users, posts, comments)
            assertThat(viewModel.cards.value).isEqualTo(expectedCards)

            // Clean up the observer
            viewModel.cards.removeObserver(cardsObserver)
        }
    }

    @Test
    fun `fetchDataAndMapCards handles API fetch error`() {
        runBlockingTest {
            // Mock the Repository's behavior to throw an exception
            `when`(repository.getUsers()).thenThrow(RuntimeException("Network Error"))

            // Set up an observer for the fetchError LiveData
            val fetchErrorObserver = Observer<Boolean> {}
            viewModel.fetchError.observeForever(fetchErrorObserver)

            // Call the function to fetch data from the API and map it to cards
            viewModel.fetchDataAndMapCards(fetchFromAPI = true)

            // Verify that the fetchError LiveData is set to true
            assertThat(viewModel.fetchError.value).isEqualTo(true)

            // Clean up the observer
            viewModel.fetchError.removeObserver(fetchErrorObserver)
        }
    }

    private fun generateTestUsers(): List<User> {
        return listOf(
            User(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                email = "john@example.com",
                phone = "555-555-5555",
                website = "https://johndoe.com"
            ),
            User(
                id = 2,
                name = "Jane Smith",
                username = "janesmith",
                email = "jane@example.com",
                phone = "444-444-4444",
                website = "https://janesmith.com"
            )
        )
    }

    private fun generateTestPosts(): List<Post> {
        return listOf(
            Post(
                userId = 1,
                id = 1,
                title = "Post 1",
                body = "This is the body of Post 1"
            ),
            Post(
                userId = 1,
                id = 2,
                title = "Post 2",
                body = "This is the body of Post 2"
            ),
            Post(
                userId = 2,
                id = 3,
                title = "Post 3",
                body = "This is the body of Post 3"
            )
        )
    }

    private fun generateTestComments(): List<Comment> {
        return listOf(
            Comment(
                postId = 1,
                id = 1,
                name = "Commenter 1",
                email = "commenter1@example.com",
                body = "This is the body of Comment 1"
            ),
            Comment(
                postId = 1,
                id = 2,
                name = "Commenter 2",
                email = "commenter2@example.com",
                body = "This is the body of Comment 2"
            ),
            Comment(
                postId = 2,
                id = 3,
                name = "Commenter 3",
                email = "commenter3@example.com",
                body = "This is the body of Comment 3"
            )
        )
    }

    private fun mapToUserCards(users: List<User>, posts: List<Post>, comments: List<Comment>): List<UserCard> {
        return users.map { user ->
            user.toUserCard().apply {
                this.posts = posts.filter { it.userId == user.id }
                    .map { post ->
                        post.toPostCard().apply {
                            this.comments = comments.filter {
                                it.postId == post.id
                            }.map { it.toCommentCard() }
                        }
                    }
            }
        }
    }
}
