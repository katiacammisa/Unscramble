package com.example.unscramble.profile.friends

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.unscramble.data.Friend
import com.example.unscramble.data.UnscrambleDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val unscrambleDatabase = UnscrambleDatabase.getDatabase(context)

    val friendList = unscrambleDatabase.friendDao().getAllFriends().asFlow()

    fun addFriend(name: String, email: String, age: String) {
        val friend = Friend(name = name, email = email, age = age, favorite = false)
        viewModelScope.launch {
            unscrambleDatabase.friendDao().insert(friend)
        }
    }

    fun deleteFriend(friend: Friend) {
        viewModelScope.launch {
            unscrambleDatabase.friendDao().delete(friend)
        }
    }

    fun favoriteFriend(friend: Friend) {
        viewModelScope.launch {
            unscrambleDatabase.friendDao().update(friend.copy(favorite = true))
        }
    }
}