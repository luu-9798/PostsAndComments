package com.luu9798.postandcomments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luu9798.postandcomments.model.card.UserCard
import com.luu9798.postandcomments.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import kotlinx.coroutines.launch
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
                val users = repository.getUsers().map { user ->
                    user.toUserCard()
                }
                _cards.postValue(users)
            } catch (e: Exception) {
                // Handle exceptions like network errors here
            }
        }
    }
}
