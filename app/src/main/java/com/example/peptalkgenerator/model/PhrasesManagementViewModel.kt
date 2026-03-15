package com.example.peptalkgenerator.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peptalkgenerator.PepTalkApplication
import com.example.peptalkgenerator.data.Phrase
import com.example.peptalkgenerator.data.PepTalkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PhrasesManagementUIState(
    val phrasesList: List<Phrase> = emptyList()
)

val PHRASE_TYPES = listOf("greeting", "first", "second", "ending")

class PhrasesManagementViewModel(
    private val pepTalkRepository: PepTalkRepository
) : ViewModel() {

    val uiState: StateFlow<PhrasesManagementUIState> =
        pepTalkRepository.getAllPhrases()
            .map { PhrasesManagementUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PhrasesManagementUIState()
            )

    var newPhraseType by mutableStateOf(PHRASE_TYPES.first())
        private set

    var newPhraseSaying by mutableStateOf("")
        private set

    fun updateNewPhraseType(type: String) {
        newPhraseType = type
    }

    fun updateNewPhraseSaying(saying: String) {
        newPhraseSaying = saying
    }

    suspend fun addPhrase() {
        if (newPhraseSaying.isNotBlank()) {
            pepTalkRepository.insertPhrase(Phrase(type = newPhraseType, saying = newPhraseSaying))
            newPhraseSaying = ""
        }
    }

    suspend fun deletePhrase(phrase: Phrase) {
        pepTalkRepository.deletePhrase(phrase)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PepTalkApplication)
                PhrasesManagementViewModel(pepTalkRepository = application.pepTalkRepository)
            }
        }
    }
}
