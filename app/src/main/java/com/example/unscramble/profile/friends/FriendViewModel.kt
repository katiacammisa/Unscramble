package com.example.unscramble.profile.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor() : ViewModel() {

    private var _friendList = MutableStateFlow(listOf<Friend>())
    val friendList = _friendList.asStateFlow()

    fun addFriend(name: String, email: String, age: String) {
        val friend = Friend(name, email, age)
        val newList = _friendList.value + friend
        viewModelScope.launch {
            _friendList.emit(newList)
        }
    }
}