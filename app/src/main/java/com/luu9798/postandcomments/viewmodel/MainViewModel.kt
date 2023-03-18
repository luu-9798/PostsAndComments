package com.luu9798.postandcomments.viewmodel

import androidx.lifecycle.ViewModel
import com.luu9798.postandcomments.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realm: Realm,
    private val repository: Repository
) : ViewModel() {

}