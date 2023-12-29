package com.example.peptalkgenerator.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/*
ViewModel for Favorites screen
 */

data class FavoritesUiState(
    val favoritesList: List<PepTalk> = listOf()
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    val favoritesUiState: StateFlow<FavoritesUiState> =
        pepTalkRepository.getFavorites().map { FavoritesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoritesUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}