package com.example.peptalkgenerator.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peptalkgenerator.data.PepTalk
import com.example.peptalkgenerator.data.PepTalkRepository
import com.example.peptalkgenerator.ui.components.PepTalkDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PepTalkDetailsUIState(
    val pepTalkDetails: PepTalkDetails = PepTalkDetails()
)

//Data class for the PepTalkDetails, being the pepTalk itself and if it is a Favorite or Blocked
data class PepTalkDetails(
    val id: Int = 0,
    var pepTalk: String = "",
    var favorite: Boolean? = null,
    var block: Boolean? = null
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

@HiltViewModel
class PepTalkDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {
    private val pepTalkId: Int =
        checkNotNull(savedStateHandle[PepTalkDetailsDestination.pepTalkIdArgs])

    val pepTalkDetailsUiState: StateFlow<PepTalkDetailsUIState> =
        pepTalkRepository.getPepTalk(pepTalkId)
            .filterNotNull()
            .map {
                PepTalkDetailsUIState(pepTalkDetails = it.toPepTalkDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PepTalkDetailsUIState()
            )

    fun removeFromFavorites() {
        viewModelScope.launch {
            val currentPepTalk = pepTalkDetailsUiState.value.pepTalkDetails.toPepTalk()
            pepTalkRepository.deletePepTalk(currentPepTalk)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}



