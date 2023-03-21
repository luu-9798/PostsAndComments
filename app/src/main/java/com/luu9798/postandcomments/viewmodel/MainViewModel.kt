package com.luu9798.postandcomments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luu9798.postandcomments.database.`object`.CommentRealm
import com.luu9798.postandcomments.database.`object`.PostRealm
import com.luu9798.postandcomments.database.`object`.UserRealm
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.model.other.Comment
import com.luu9798.postandcomments.model.other.Post
import com.luu9798.postandcomments.model.user.User
import com.luu9798.postandcomments.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realm: Realm,
    private val repository: Repository
) : ViewModel() {

    private val _cards = MutableLiveData<List<UserCard>>()
    val cards: LiveData<List<UserCard>> = _cards

    fun fetchDataAndMapCards() {
        viewModelScope.launch {
            try {
                val usersDeferred = async { repository.getUsers() }
                val postsDeferred = async { repository.getPosts() }
                val commentsDeferred = async { repository.getComments() }

                val posts = postsDeferred.await()
                val comments = commentsDeferred.await()
                val users =  usersDeferred.await()

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
            } catch (e: Exception) {
                // Handle exceptions like network errors here
            }
        }
    }

    private suspend fun saveUsersToDatabase(users: List<User>) = withContext(Dispatchers.IO) {
        realm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(users.map { it.toRealm() })
        }
    }

    private suspend fun savePostsToDatabase(posts: List<Post>) = withContext(Dispatchers.IO) {
        realm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(posts.map { it.toRealm() })
        }
    }

    private suspend fun saveCommentsToDatabase(comments: List<Comment>) = withContext(Dispatchers.IO) {
        realm.executeTransactionAsync { realm ->
            realm.insertOrUpdate(comments.map { it.toRealm() })
        }
    }

    private suspend fun getUsersFromDatabase(): List<UserRealm> = withContext(Dispatchers.IO) {
        val realmResults = realm.where(UserRealm::class.java).findAll()
        return@withContext realm.copyFromRealm(realmResults)
    }

    private suspend fun getPostsFromDatabase(): List<PostRealm> = withContext(Dispatchers.IO) {
        val realmResults = realm.where(PostRealm::class.java).findAll()
        return@withContext realm.copyFromRealm(realmResults)
    }

    private suspend fun getCommentsFromDatabase(): List<CommentRealm> = withContext(Dispatchers.IO) {
        val realmResults = realm.where(CommentRealm::class.java).findAll()
        return@withContext realm.copyFromRealm(realmResults)
    }
}
