package com.example.peptalkgenerator.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/*
ViewModel for Favorites screen
 */

data class FavoritesUiState(
    val favoritesList: List<PepTalk> = listOf()
)

class FavoritesViewModel(
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

        //Setup ViewModel Factory
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)
                val pepTalkRepository = application.pepTalkRepository
                FavoritesViewModel(pepTalkRepository = pepTalkRepository)
            }
        }
    }
}