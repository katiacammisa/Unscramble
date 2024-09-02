package com.example.unscramble.ranking

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unscramble.apiManager.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiServiceImpl: ApiServiceImpl,
): ViewModel() {

    private val _loadingRanking = MutableStateFlow(false)
    val loadingRanking = _loadingRanking.asStateFlow()

    private val _ranking = MutableStateFlow(listOf<RankingModel>())
    val ranking = _ranking.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        loadRanking()
    }

    fun retryLoadingRanking() {
        loadRanking()
    }

    private fun loadRanking() {
        _loadingRanking.value = true
        apiServiceImpl.getRanking(
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    _ranking.emit(it.sortedByDescending { it.ranking })
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingRanking.value = false
            }
        )
    }
}