package com.luu9798.postandcomments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luu9798.postandcomments.database.RealmManager
import com.luu9798.postandcomments.database.`object`.CommentRealm
import com.luu9798.postandcomments.database.`object`.PostRealm
import com.luu9798.postandcomments.database.`object`.UserRealm
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import com.luu9798.postandcomments.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realmManager: RealmManager,
    private val repository: Repository
) : ViewModel() {

    private val _cards = MutableLiveData<List<UserCard>>()
    val cards: LiveData<List<UserCard>> = _cards

    private val _fetchError = MutableLiveData(false)
    val fetchError: LiveData<Boolean> = _fetchError

    var userCount: Int = 0
    var postCount: Int = 0
    var commentCount: Int = 0

    fun fetchDataAndMapCards(fetchFromAPI: Boolean) {
        if (fetchFromAPI) {
            fetchDataFromApi()
        } else {
            fetchDataFromDatabase()
        }
    }

    private fun fetchDataFromDatabase() {
        viewModelScope.launch {
            try {
                val users = getUsersFromDatabase().map { it.toUser() }
                val posts = getPostsFromDatabase().map { it.toPost() }
                val comments = getCommentsFromDatabase().map { it.toComment() }

                mapCard(users, posts, comments)
            } catch (e: Exception) {
                _fetchError.postValue(true)
            }
        }
    }

    private fun fetchDataFromApi() {
        viewModelScope.launch {
            try {
                val usersDeferred = async { repository.getUsers() }
                val postsDeferred = async { repository.getPosts() }
                val commentsDeferred = async { repository.getComments() }

                val posts = postsDeferred.await()
                val comments = commentsDeferred.await()
                val users =  usersDeferred.await()

                mapCard(users, posts, comments)
                saveToDatabase(users, posts, comments)
            } catch (e: Exception) {
                _fetchError.postValue(true)
            }
        }
    }

    private fun saveToDatabase(users: List<User>, posts: List<Post>, comments: List<Comment>) {
        viewModelScope.launch {
            saveUsersToDatabase(users)
            savePostsToDatabase(posts)
            saveCommentsToDatabase(comments)
        }
    }

    private fun mapCard(users: List<User>, posts: List<Post>, comments: List<Comment>) {
        userCount = users.size
        postCount = posts.size
        commentCount = comments.size
        val cards = users.map { user ->
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
        _cards.postValue(cards)
    }

    private suspend fun saveUsersToDatabase(users: List<User>) = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insertOrUpdate(users.map { it.toRealm() })
        }
        realmManager.closeRealmInstance()
    }

    private suspend fun savePostsToDatabase(posts: List<Post>) = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insertOrUpdate(posts.map { it.toRealm() })
        }
        realmManager.closeRealmInstance()
    }

    private suspend fun saveCommentsToDatabase(comments: List<Comment>) = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insertOrUpdate(comments.map { it.toRealm() })
        }
        realmManager.closeRealmInstance()
    }

    private suspend fun getUsersFromDatabase(): List<UserRealm> = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        val realmResults = realm.where(UserRealm::class.java).findAll()
        val result = realm.copyFromRealm(realmResults)
        realmManager.closeRealmInstance()
        return@withContext result
    }

    private suspend fun getPostsFromDatabase(): List<PostRealm> = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        val realmResults = realm.where(PostRealm::class.java).findAll()
        val result = realm.copyFromRealm(realmResults)
        realmManager.closeRealmInstance()
        return@withContext result
    }

    private suspend fun getCommentsFromDatabase(): List<CommentRealm> = withContext(Dispatchers.IO) {
        val realm = realmManager.getRealmInstance()
        val realmResults = realm.where(CommentRealm::class.java).findAll()
        val result = realm.copyFromRealm(realmResults)
        realmManager.closeRealmInstance()
        return@withContext result
    }
}
