package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.launch

class PepTalkScreenViewModel(
    private val pepTalkRepository: PepTalkRepository,
    pendingNotificationTalk: String? = null
) : ViewModel() {

    private val _talkState = mutableStateOf("")
    val talkState: String by _talkState

    var pepTalkUiState by mutableStateOf(PepTalkUiState())
        private set

    init {
        if (pendingNotificationTalk != null) {
            updateTalk(pendingNotificationTalk)
        } else {
            refreshTalkState()
        }
    }

    fun refreshTalkState() {
        viewModelScope.launch {
            updateTalk(pepTalkRepository.generateNewTalk())
        }
    }

    private fun updateTalk(talk: String) {
        _talkState.value = talk
        pepTalkUiState = PepTalkUiState(
            pepTalkDetails = PepTalkDetails(
                pepTalk = talk,
                favorite = true,
                block = false
            )
        )
    }

    suspend fun favoritePepTalk() {
        pepTalkRepository.insertPepTalk(pepTalkUiState.pepTalkDetails.toPepTalk())
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
    val pepTalkDetails: PepTalkDetails = PepTalkDetails()
)
