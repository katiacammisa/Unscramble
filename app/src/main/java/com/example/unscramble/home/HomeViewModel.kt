package com.example.unscramble.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.data.PreferencesKeys
import com.example.unscramble.data.getFromDataStore
import com.example.unscramble.data.saveToDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
): ViewModel() {

    private var _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    init {
        getNameFromDataStore()
    }

    private fun getNameFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.USER_NAME_KEY).collect {
                _userName.value = it ?: ""
            }
        }
    }

    fun saveToDataStore(username: String) {
        viewModelScope.launch {
            saveToDataStore(context, username, PreferencesKeys.USER_NAME_KEY)
            _userName.value = username
        }
    }
}