package app.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.peptalkgenerator.PepTalkApplication
import app.peptalkgenerator.data.PepTalk
import app.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PepTalkScreenViewModel(
    private val pepTalkRepository: PepTalkRepository,
    pendingNotificationTalk: String? = null
) : ViewModel() {

    private val _talkState = mutableStateOf("")
    val talkState: String by _talkState

    var pepTalkUiState by mutableStateOf(PepTalkUiState())
        private set

    private val blockedTexts: StateFlow<List<String>> =
        pepTalkRepository.getBlocked()
            .map { list -> list.map { it.pepTalk } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

    init {
        if (pendingNotificationTalk != null) {
            updateTalk(pendingNotificationTalk, isFromNotification = true)
        } else {
            refreshTalkState()
        }
    }

    fun refreshTalkState() {
        viewModelScope.launch {
            updateTalk(
                pepTalkRepository.generateNewTalkExcluding(blockedTexts.value),
                isFromNotification = false
            )
        }
    }

    private fun updateTalk(talk: String, isFromNotification: Boolean = false) {
        _talkState.value = talk
        pepTalkUiState = PepTalkUiState(
            pepTalkDetails = PepTalkDetails(
                pepTalk = talk,
                favorite = true,
                block = false
            ),
            isFromNotification = isFromNotification,
            isFavorited = false
        )
    }

    suspend fun favoritePepTalk() {
        pepTalkRepository.insertPepTalk(pepTalkUiState.pepTalkDetails.toPepTalk())
        pepTalkUiState = pepTalkUiState.copy(isFavorited = true)
    }

    suspend fun blockPepTalk() {
        if (pepTalkUiState.isFavorited) return
        pepTalkRepository.insertPepTalk(
            PepTalk(
                pepTalk = pepTalkUiState.pepTalkDetails.pepTalk,
                favorite = false,
                block = true
            )
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PepTalkApplication)
                val pendingTalk = application.pendingNotificationTalk
                application.pendingNotificationTalk = null  // consume it
                PepTalkScreenViewModel(
                    pepTalkRepository = application.pepTalkRepository,
                    pendingNotificationTalk = pendingTalk
                )
            }
        }
    }
}

data class PepTalkUiState(
    val pepTalkDetails: PepTalkDetails = PepTalkDetails(),
    val isFromNotification: Boolean = false,
    val isFavorited: Boolean = false
)
