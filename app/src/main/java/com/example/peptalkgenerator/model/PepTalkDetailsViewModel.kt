package com.example.peptalkgenerator.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.ui.components.PepTalkDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PepTalkDetailsUIState(
    val pepTalkDetails: PepTalkDetails = PepTalkDetails()
)

data class PepTalkDetails(
    val id: Int = 0,
    val pepTalk: String = "",
    val favorite: Boolean? = false,
    val block: Boolean? = false
)

//Extension functions to convert PepTalkDetails to PepTalk
fun PepTalkDetails.toPepTalk(): PepTalk = PepTalk(
    id = id,
    pepTalk = pepTalk,
    favorite = favorite,
    block = block
)

//Another Extension func to go from PepTalk to PepTalk details
fun PepTalk.toPepTalkDetails(): PepTalkDetails = PepTalkDetails(
    id = id,
    pepTalk = pepTalk,
    favorite = favorite,
    block = block
)

class PepTalkDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    val pepTalkRepository: PepTalkRepository
) : ViewModel(){
    private val pepTalkId: Int = checkNotNull(savedStateHandle[PepTalkDetailsDestination.pepTalkIdArgs])

    val pepTalkDetailsUiState: StateFlow<PepTalkDetailsUIState> =
        pepTalkRepository.getPepTalk(pepTalkId)
            .filterNotNull()
            .map{
                PepTalkDetailsUIState(pepTalkDetails = it.toPepTalkDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PepTalkDetailsUIState()
            )

    fun removeFromFavorites(){
        viewModelScope.launch {
            val currentPepTalk = pepTalkDetailsUiState.value.pepTalkDetails.toPepTalk()
            pepTalkRepository.updatePepTalk(currentPepTalk.copy(favorite = false))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)
                val pepTalkRepository = application.pepTalkRepository
                PepTalkDetailsViewModel(
                    this.createSavedStateHandle(),
                    pepTalkRepository = pepTalkRepository
                )
            }
        }
    }
}



